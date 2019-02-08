package priv.zxy.moonstep.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.framework.race.Race;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/3
 * 描述: 用来解析服务器返回JSON数据的工具类
 **/

public class JSONUtil {

    /**
     *  将返回的JSON数据解析成Pet实体类
     */
    public static Pet handlePetResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            String PetContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(PetContent, Pet.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
