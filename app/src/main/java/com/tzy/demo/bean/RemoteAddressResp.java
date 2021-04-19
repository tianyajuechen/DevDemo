package com.tzy.demo.bean;

/**
 * @Author tangzhaoyang
 * @Date 2021/3/9
 */
public class RemoteAddressResp {

    /**
     * code : 200
     * msg : ok
     * result : {"id":43,"created_at":"2021-03-09T17:47:41+08:00","updated_at":"2021-03-09T17:47:41+08:00","deleted_at":null,"name":"hh","avator":"http://www.baidu.com/logo.png","source_ip":"10.191.72.233","to_id":"robot","visitor_id":"976daf9a-ad76-42ec-9c1d-82c98b782bce","status":1,"refer":"客户端访问","city":"客户端访问","client_ip":"10.191.72.233","uid":"2322","extra":""}
     * socket : ws://10.191.72.156/ws_visitor?visitor_id=976daf9a-ad76-42ec-9c1d-82c98b782bce
     */

    private int code;
    private String msg;
    private ResultBean result;
    private String socket;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public static class ResultBean {
        /**
         * id : 43
         * created_at : 2021-03-09T17:47:41+08:00
         * updated_at : 2021-03-09T17:47:41+08:00
         * deleted_at : null
         * name : hh
         * avator : http://www.baidu.com/logo.png
         * source_ip : 10.191.72.233
         * to_id : robot
         * visitor_id : 976daf9a-ad76-42ec-9c1d-82c98b782bce
         * status : 1
         * refer : 客户端访问
         * city : 客户端访问
         * client_ip : 10.191.72.233
         * uid : 2322
         * extra :
         */

        private String id;
        private String created_at;
        private String updated_at;
        private String deleted_at;
        private String name;
        private String avator;
        private String source_ip;
        private String to_id;
        private String visitor_id;
        private int status;
        private String refer;
        private String city;
        private String client_ip;
        private String uid;
        private String extra;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(String deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getSource_ip() {
            return source_ip;
        }

        public void setSource_ip(String source_ip) {
            this.source_ip = source_ip;
        }

        public String getTo_id() {
            return to_id;
        }

        public void setTo_id(String to_id) {
            this.to_id = to_id;
        }

        public String getVisitor_id() {
            return visitor_id;
        }

        public void setVisitor_id(String visitor_id) {
            this.visitor_id = visitor_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefer() {
            return refer;
        }

        public void setRefer(String refer) {
            this.refer = refer;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClient_ip() {
            return client_ip;
        }

        public void setClient_ip(String client_ip) {
            this.client_ip = client_ip;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}
