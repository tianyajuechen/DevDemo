package com.tzy.demo.activity.wallpaper

import android.content.Context
import android.content.res.Configuration
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.joints.MouseJoint
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef
import com.tzy.demo.bean.Box2dInfoBean
import javax.microedition.khronos.opengles.GL10


class Box2dEffectView(private val context: Context) : ApplicationListener {

    private lateinit var camera: OrthographicCamera
    private lateinit var debugRenderer: Box2DDebugRenderer
    private lateinit var world: World
    private lateinit var box2dSensorLogic: Box2dSensorLogic
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var textureRegion: TextureRegion
    private val bodyList = mutableListOf<Body>()
    private var isDebugRenderer = false
    private var _canDraw = true

    private lateinit var bgImg: Texture //背景图
    private var touchBody: Body? = null //触摸的body
    private lateinit var groundBody: Body //地面body
    private val touchVector = Vector3() //触摸点坐标
    private var mouseJoint: MouseJoint? = null
    private val target = Vector2() //新的坐标

    private val inputAdapter = object : InputAdapter() {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            //转换坐标
            touchVector.set(screenX.toFloat(), screenY.toFloat(), 0F)
            camera.unproject(touchVector)

            //查找坐标附近的body
            touchBody = null
            world.QueryAABB(callback, touchVector.x - 0.1f, touchVector.y - 0.1f, touchVector.x + 0.1f, touchVector.y + 0.1f)

            if (touchBody != null) {
                val def = MouseJointDef().apply {
                    bodyA = groundBody
                    bodyB = touchBody
                    collideConnected = true
                    target.set(touchVector.x, touchVector.y)
                    maxForce = 10000F * touchBody!!.mass
                }
                mouseJoint = world.createJoint(def) as MouseJoint
                touchBody!!.isAwake = true
            }
            return false
        }

        override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
            if (mouseJoint != null) {
                touchVector.set(screenX.toFloat(), screenY.toFloat(), 0F)
                camera.unproject(touchVector)
                mouseJoint!!.target = target.set(touchVector.x, touchVector.y)
            }
            return false
        }

        override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            if (mouseJoint != null) {
                world.destroyJoint(mouseJoint)
                mouseJoint = null
            }
            return false
        }
    }

    /**
     * 查询坐标点附件的body
     * 返回 true:继续查询 false:终止查询
     */
    private val callback = QueryCallback {
        val userData = it.body.userData
        //如果是四边的墙，继续查
        if (WALL == userData) return@QueryCallback true
        if (it.testPoint(touchVector.x, touchVector.y)) {
            touchBody = it.body
            return@QueryCallback false
        }
        return@QueryCallback true
    }

    companion object {
        const val TAG = "Box2dEffectView"
        const val PXTM = 60F //1米等于60像素
        const val MAX_BODY_COUNT = 80
        const val WALL = "wall"
        const val MIN_OFFSET = 50
    }

    override fun create() {
        val width = Gdx.graphics.width
        val height = Gdx.graphics.height

        bgImg = Texture(Gdx.files.internal("bg/1.png"))
        val texture = Texture(Gdx.files.internal("particle/1.png"))
        textureRegion = TextureRegion(texture)

        val cameraWidth = width / PXTM
        val cameraHeight = height / PXTM
        camera = OrthographicCamera(cameraWidth, cameraHeight)
        debugRenderer = Box2DDebugRenderer()
        spriteBatch = SpriteBatch()
        world = World(Vector2(0F, -120F), false)

        addWall(0F, camera.viewportHeight / 2, camera.viewportWidth, 1 / PXTM)
        groundBody = addWall(0F, -camera.viewportHeight / 2, camera.viewportWidth, 1 / PXTM)
        addWall(-camera.viewportWidth / 2, 0F, 1 / PXTM, camera.viewportHeight)
        addWall(camera.viewportWidth / 2, 0F, 1 / PXTM, camera.viewportHeight)

        box2dSensorLogic = Box2dSensorLogic(world, context)

        Gdx.input.inputProcessor = inputAdapter
    }

    override fun resize(width: Int, height: Int) {
        val configuration = context.resources.configuration
        box2dSensorLogic.isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    override fun render() {
        Gdx.gl.glClearColor(0F, 0F, 0F, 0F)
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

        synchronized(Box2dEffectView::class.java) {
            if (!_canDraw) {
                destroyAll()
                return
            }
            if (bodyList.isEmpty()) release()
            val deltaTime = Gdx.app.graphics.deltaTime
            world.step(deltaTime, 6, 2)

            drawBody(deltaTime)

            if (isDebugRenderer) {
                debugRenderer.render(world, camera.combined)
            }
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

    /**
     * 添加墙(边界)
     */
    private fun addWall(x: Float, y: Float, hx: Float, hy: Float): Body {
        val bodyDef = BodyDef().apply {
            position.set(Vector2(x, y))
        }
        val body = world.createBody(bodyDef)
        val shape = PolygonShape().apply {
            setAsBox(hx, hy)
        }
        body.userData = WALL
        body.createFixture(shape, 0.5F)
        shape.dispose()
        return body
    }

    /**
     * 绘制Body
     */
    private fun drawBody(deltaTime: Float) {
        spriteBatch.begin()
        spriteBatch.draw(bgImg, 0F, 0F, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        for (body in bodyList) {
            val userData = body.userData as Box2dInfoBean
            //更新时间
            userData.runtimes += deltaTime

            //body坐标远点是中心点，单位是米，libgdx坐标远点在左下角，单位是像素，所以这里需要转换下
            val vector2 = Transform.mtp(body.position.x, body.position.y, Vector2(1F, 1F), PXTM)
            val widthSize = 60F
            spriteBatch.setColor(1F, 1F, 1F, 1F)
            val angle = (body.transform.rotation * 180 / Math.PI).toFloat()
            spriteBatch.draw(textureRegion, vector2.x, vector2.y, widthSize, widthSize, widthSize * 2, widthSize * 2, 1F, 1F, angle)
        }
        spriteBatch.end()
    }

    public fun addBody(isLeft: Boolean) {
        if (!_canDraw) return
        totalLimitsLogic()
        synchronized(Box2dEffectView::class.java) {
            val bodyDef = BodyDef().apply {
                type = BodyDef.BodyType.DynamicBody
                val thrownXRandom = Math.random().toFloat() * 30 + 4 //初始x速度
                val thrownYRandom = -(Math.random().toFloat() * 20 + 3) //初始y速度
                val yRandomStart = Math.random().toFloat() * 8 //初始y位置
                if (isLeft) {
                    linearVelocity.set(thrownXRandom, thrownYRandom)
                    position.set(Vector2(-camera.viewportWidth / 2 + 2, camera.viewportHeight / 2 - yRandomStart))
                } else {
                    linearVelocity.set(-thrownXRandom, thrownYRandom)
                    position.set(Vector2(camera.viewportWidth / 2 - 2, camera.viewportHeight / 2 - yRandomStart))
                }
            }
            val body = world.createBody(bodyDef)
            body.userData = Box2dInfoBean()
            body.isFixedRotation = false
            val shape = CircleShape().apply { radius = 1F }
            val fixtureDef = FixtureDef().apply {
                this.shape = shape
                density = 1.5F
                friction = 0.3F
                restitution = 0.5F
            }
            body.createFixture(fixtureDef)
            shape.dispose()

            bodyList.add(body)
            if (bodyList.size == 1) {
                box2dSensorLogic.startListener()
            }
        }
    }

    private fun destroyBody(index: Int) {
        if (index < 0 || index >= bodyList.size) return
        val body = bodyList.removeAt(index)
        world.destroyBody(body)
        if (bodyList.isEmpty()) {
            box2dSensorLogic.stopListener()
        }
    }

    private fun totalLimitsLogic() {
        synchronized(Box2dEffectView::class.java) {
            if (bodyList.size > MAX_BODY_COUNT) {
                destroyBody(0)
            }
        }
    }

    private fun destroyAll() {
        synchronized(Box2dEffectView::class.java) {
            while (bodyList.isNotEmpty()) {
                destroyBody(0)
            }
        }
    }

    /**
     * 设置是否可以绘制
     */
    fun setCanDraw(canDraw: Boolean) {
        _canDraw = canDraw
        if (!_canDraw) {
            destroyAll()
        }
    }

    /**
     * 开启debug模式渲染，能看到body轮廓
     */
    fun openDebugRenderer(debugRenderer: Boolean) {
        isDebugRenderer = debugRenderer
    }

    fun release() {
        box2dSensorLogic.release()
    }
}