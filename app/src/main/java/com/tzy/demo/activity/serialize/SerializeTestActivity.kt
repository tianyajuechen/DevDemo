package com.tzy.demo.activity.serialize

import com.google.gson.Gson
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivitySerializeTestBinding
import java.io.*

class SerializeTestActivity : BaseActivity<ActivitySerializeTestBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_serialize_test
    }

    override fun initView() {

    }

    override fun initEvent() {
        mBinding.btSave.setOnClickListener {
            save()
        }

        mBinding.btGet.setOnClickListener {
            getTxt()
        }
    }

    private fun save() {
        val user = User("张三", 18)
        val file = File(externalCacheDir, "user.txt")
        val objectOutputStream = ObjectOutputStream(FileOutputStream(file))
        objectOutputStream.writeObject(user)
        objectOutputStream.close()

        mBinding.tvMsg.text = Gson().toJson(user)
    }

    private fun getTxt() {
        val file = File(externalCacheDir, "user.txt")
        try {
            val objectInputStream = ObjectInputStream(FileInputStream(file))
            val user = objectInputStream.readObject() as User
            objectInputStream.close()

            mBinding.tvMsg2.text = Gson().toJson(user)
        } catch (e: Exception) {
            mBinding.tvMsg2.text = e.toString()
        }
    }

    private data class User(
        val name: String,
        val age: Int,
    ) : java.io.Serializable {

        companion object {
//            private const val serialVersionUID = 1L
        }

        var color = "red"
        var size = "max"

        fun getSize(): Int {
            return name.length + age.toString().length + 10
        }

        override fun toString(): String {
            return "User(name='$name', age=$age)"
        }
    }
}