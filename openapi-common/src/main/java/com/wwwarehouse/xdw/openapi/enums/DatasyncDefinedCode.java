package com.wwwarehouse.xdw.openapi.enums;

public class DatasyncDefinedCode {
    public enum PRINT_MODEL {
        CAI_NIAO_MODEL("CaiNiao_MODEL", "菜鸟面单", null),
        JPG_MODEL("Jpg_MODEL", "打印jpg图片", null),
        PDF_MODEL("PDF_MODEL", "PDF模式", null),
        TEMPLET_MODEL("TEMPLET_MODEL", "模板模式", null),
        WORD_MODEL("Word_MODEL", "word打印", null),;
        private String code;
        private String name;
        private String handleCode;

        PRINT_MODEL(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static PRINT_MODEL getByCode(String code) {
            for (PRINT_MODEL item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum ATTRIBUTE_NAME {
        ATTRIBUTE_NAME_ADDRESS("ATTRIBUTE_NAME_ADDRESS", "属性-地址", null),
        ATTRIBUTE_NAME_AREA("ATTRIBUTE_NAME_AREA", "属性-面积", null),;
        private String code;
        private String name;
        private String handleCode;

        ATTRIBUTE_NAME(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static ATTRIBUTE_NAME getByCode(String code) {
            for (ATTRIBUTE_NAME item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum PRINT_TYPE {
        CARDS("CARDS", "贺卡", null),
        DETAIL_LIST("DETAIL_LIST", "明细单", null),
        EXPRESS_LIST("EXPRESS_LIST", "快递单", null),
        INVOICE("INVOICE", "发票", null),
        PACKING_LIST("PACKING_LIST", "装箱单", null),
        PICK_LIST("PICK_LIST", "拣选单", null),;
        private String code;
        private String name;
        private String handleCode;

        PRINT_TYPE(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static PRINT_TYPE getByCode(String code) {
            for (PRINT_TYPE item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum PARAM_FEATURE_CODE {
        AppOwner("20171220", "APP拥有者", null),;
        private String code;
        private String name;
        private String handleCode;

        PARAM_FEATURE_CODE(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static PARAM_FEATURE_CODE getByCode(String code) {
            for (PARAM_FEATURE_CODE item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum APP {
        VORES("vores", "VORES我俩", "260002"),
        WWWAREHOUSE("wwwarehouse", "网仓3号", "260001"),;
        private String code;
        private String name;
        private String handleCode;

        APP(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static APP getByCode(String code) {
            for (APP item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }

    public enum WH_APP_TYPE {
        WH_VORES_ANDROID("wh_vores_android", "VORES我俩", "vores"),
        WH_VORES_H5("wh_vores_h5", "VORES我俩", "vores"),
        WH_VORES_IOS("wh_vores_ios", "VORES我俩", "vores"),
        WH_VORES_WEB("wh_vores_web", "VORES我俩", "vores"),
        WH_WH3_ANDROID("wh_wh3_android", "网仓3号", "wwwarehouse"),
        WH_WH3CO_IOS("wh_wh3co_ios", "网仓3号", "wwwarehouse"),
        WH_WH3_H5("wh_wh3_h5", "网仓3号", "wwwarehouse"),
        WH_WH3_IOS("wh_wh3_ios", "网仓3号", "wwwarehouse"),
        WH_WH3_WEB("wh_wh3_web", "网仓3号", "wwwarehouse"),;
        private String code;
        private String name;
        private String handleCode;

        WH_APP_TYPE(String code, String name, String handleCode) {
            this.code = code;
            this.name = name;
            this.handleCode = handleCode;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getHandleCode() {
            return this.handleCode;
        }

        @Override
        public String toString() {
            return ("handleCode = " + this.handleCode + ",name = " + this.name + ",code = " + this.code);
        }

        public boolean equalByCode(String code) {
            return this.code != null && this.code.equals(code);
        }

        public static WH_APP_TYPE getByCode(String code) {
            for (WH_APP_TYPE item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }
}
