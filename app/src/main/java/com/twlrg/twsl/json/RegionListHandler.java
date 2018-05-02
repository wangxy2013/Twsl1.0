package com.twlrg.twsl.json;



import com.twlrg.twsl.entity.RegionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class RegionListHandler extends JsonHandler
{
    private List<RegionInfo> regionInfoList = new ArrayList<>();

    public List<RegionInfo> getRegionInfoList()
    {
        return regionInfoList;
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
                    RegionInfo mRegionInfo = new RegionInfo(arr.optJSONObject(i));
                    regionInfoList.add(mRegionInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
