package com.febino.validation;

import android.content.Context;

import com.febino.aquafish.R;
import com.febino.dataclass.OrderDetails;

public class ValidateDetails {
    public static String ValidateOrderDetails(Context context, String box, String kg, String rate) {
        String message = null;
        int boxInt;
        float kgFloat;
        float rateFloat;

        if(box.equals("") || box.equals(" ")){
            message = context.getResources().getString(R.string.give_input_box);
            return message;
        }
        else if(kg.equals(" ") || kg.equals("")){
            message = context.getResources().getString(R.string.give_input_kg);
            return message;
        }
        else if(rate.equals(" ") || rate.equals("")){
            message = context.getResources().getString(R.string.give_input_rate);
            return message;
        }


        try {
            boxInt = Integer.parseInt(box);
            kgFloat = Float.parseFloat(kg);
            rateFloat = Float.parseFloat(rate);
        } catch (Exception e) {
            e.printStackTrace();
            message = context.getResources().getString(R.string.not_valid_input);
            return message;
        }

        if(boxInt == 0 && rateFloat == 0 && kgFloat == 0){
            message = context.getResources().getString(R.string.zero_not_accept);
        }

        return message;
    }
}
