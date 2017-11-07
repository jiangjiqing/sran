package com.hongshen.sran_service.service.util;

import com.hongshen.sran_service.service.util.impl.NetObj_Unicom_Lte;
import com.hongshen.sran_service.service.util.impl.NetObj_Unicom_Wcdma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by poplar on 17-10-30.
 */
@Controller
public class NetObjFactory {
    @Autowired
    private NetObj_Unicom_Lte netObj_Unicom_Lte;
    @Autowired
    private NetObj_Unicom_Wcdma netObj_Unicom_Wcdma;
   public NetObjBase getNetObj(String supplier , String generation){
       switch (supplier) {
                case Constant.UNICM://中国联通
                    switch (generation)
                    {
                        case Constant.WCDMA://3G
                            return netObj_Unicom_Wcdma;
                        case Constant.LTE://4G
                            return netObj_Unicom_Lte;
                        default:
                           return null;
                    }
                default:
                   return null;
        }

   }
}
