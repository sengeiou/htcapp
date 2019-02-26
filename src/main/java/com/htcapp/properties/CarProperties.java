package com.htcapp.properties;


/**
 * 保存所有车牌名
 */
public class CarProperties {

    public static final String[] carRegions={
            "京","津","冀","晋","蒙","辽","吉","黑","沪","苏","浙",
            "皖","闽","赣","鲁","豫","鄂","湘","粤","桂","琼","渝",
            "川","贵","云","藏","陕","甘","青","宁","新","港","澳",
            "台","警","使","WJ","领","学"
    };

    public static boolean getCar(String carVal){
        int length=carRegions.length;
        for(int i=0;i<length;i++){
            if (carRegions[i].equals(carVal)){
                return true;
            }
        }
        return false;
    }
}
