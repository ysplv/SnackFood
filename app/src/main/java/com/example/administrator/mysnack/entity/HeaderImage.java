package com.example.administrator.mysnack.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class HeaderImage {

    /**
     * rs_code : 1000
     * data : {"count":4,"items":[{"id":262,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/96c/6c/c/cf4a1c3edfc324e65973ab4f368fe96c.jpg","img_w":300,"img_h":152},"action":{"type":4,"info":"506"}},{"id":269,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/068/68/8/fe26fbe5ba7492c525a6e7b0f89f0068.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"515"}},{"id":274,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/c8e/8e/e/cd212ed289d8fc3af9e2e47c55734c8e.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"521"}},{"id":267,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/6fb/fb/b/f1b58a39f812f10672937dec293f36fb.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"513"}}]}
     * rs_msg : success
     */

    private String rs_code;
    /**
     * count : 4
     * items : [{"id":262,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/96c/6c/c/cf4a1c3edfc324e65973ab4f368fe96c.jpg","img_w":300,"img_h":152},"action":{"type":4,"info":"506"}},{"id":269,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/068/68/8/fe26fbe5ba7492c525a6e7b0f89f0068.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"515"}},{"id":274,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/c8e/8e/e/cd212ed289d8fc3af9e2e47c55734c8e.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"521"}},{"id":267,"title":"","desc":"","img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/6fb/fb/b/f1b58a39f812f10672937dec293f36fb.jpg","img_w":308,"img_h":160},"action":{"type":4,"info":"513"}}]
     */

    private DataBean data;
    private String rs_msg;

    public String getRs_code() {
        return rs_code;
    }

    public void setRs_code(String rs_code) {
        this.rs_code = rs_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRs_msg() {
        return rs_msg;
    }

    public void setRs_msg(String rs_msg) {
        this.rs_msg = rs_msg;
    }

    public static class DataBean {
        private int count;
        /**
         * id : 262
         * title :
         * desc :
         * img : {"img_url":"http://img.lingshi.cccwei.com/lingshi/96c/6c/c/cf4a1c3edfc324e65973ab4f368fe96c.jpg","img_w":300,"img_h":152}
         * action : {"type":4,"info":"506"}
         */

        private List<ItemsBean> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int id;
            private String title;
            private String desc;
            /**
             * img_url : http://img.lingshi.cccwei.com/lingshi/96c/6c/c/cf4a1c3edfc324e65973ab4f368fe96c.jpg
             * img_w : 300
             * img_h : 152
             */

            private ImgBean img;
            /**
             * type : 4
             * info : 506
             */

            private ActionBean action;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public ImgBean getImg() {
                return img;
            }

            public void setImg(ImgBean img) {
                this.img = img;
            }

            public ActionBean getAction() {
                return action;
            }

            public void setAction(ActionBean action) {
                this.action = action;
            }

            public static class ImgBean {
                private String img_url;
                private int img_w;
                private int img_h;

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public int getImg_w() {
                    return img_w;
                }

                public void setImg_w(int img_w) {
                    this.img_w = img_w;
                }

                public int getImg_h() {
                    return img_h;
                }

                public void setImg_h(int img_h) {
                    this.img_h = img_h;
                }
            }

            public static class ActionBean {
                private int type;
                private String info;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }
            }
        }
    }
}
