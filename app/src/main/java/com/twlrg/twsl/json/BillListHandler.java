package com.twlrg.twsl.json;


import com.twlrg.twsl.entity.BillInfo;
import com.twlrg.twsl.entity.CommentInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class BillListHandler extends JsonHandler
{
    private List<BillInfo> billInfoList = new ArrayList<>();

    public List<BillInfo> getBillInfoList()
    {
        return billInfoList;
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
                    BillInfo mCommentInfo = new BillInfo(arr.optJSONObject(i));
                    billInfoList.add(mCommentInfo);
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
