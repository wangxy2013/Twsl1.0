package com.twlrg.twsl.json;


import com.twlrg.twsl.entity.BillInfo;
import com.twlrg.twsl.entity.RoomInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class RoomListHandler extends JsonHandler
{
    private List<RoomInfo> roomInfoList = new ArrayList<>();

    public List<RoomInfo> getRoomInfoList()
    {
        return roomInfoList;
    }

    @Override
    protected void parseJson(JSONObject jsonObj) throws Exception
    {
        try
        {
            JSONArray arr = jsonObj.optJSONArray("data");


            if (null != arr)
            {
                for (int i = 0; i < arr.length(); i++)
                {
                    RoomInfo mRoomInfo = new RoomInfo(arr.optJSONObject(i));
                    roomInfoList.add(mRoomInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
