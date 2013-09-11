package cn.edu.sdu.drs.bean;

/**
 * 
 * @author join
 *
 */

public enum Gender {

    /* 男 */
    MAN {
        @Override
        public String getName() {
            return "男";
        }
    },

    /* 女 */
    WOMEN {
        @Override
        public String getName() {
            return "女";
        }
    },

    /* 男女不限 */
    NONE {
        @Override
        public String getName() {
            return "男女不限";
        }
    };

    /** abstract method getName  **/

    public abstract String getName();

    /**
     * 通过名称得到相应的枚举实例
     * @param name
     * @return
     */
    public static Gender getGenderByName(String name){
        if(name != null && !"".equals(name)){
            if(name.equals("男")){
                return MAN;
            }
            if(name.equals("女")){
                return WOMEN;
            }
            if(name.equals("男女不限")){
                return NONE;
            }
        }
        return null;
    }
}
