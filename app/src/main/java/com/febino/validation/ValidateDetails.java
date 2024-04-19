package com.febino.validation;

import android.content.Context;

import com.febino.aquafish.R;
import com.febino.dataclass.OrderDetails;

public class ValidateDetails {
    public static String ValidateOrderDetails(Context context, String box, String kg, String rate) {
        String message = null;
        if(box.equals("") || box.equals(" ")){
            message = context.getResources().getString(R.string.give_input_box);
        }
        else if(kg.equals(" ") || kg.equals("")){
            message = context.getResources().getString(R.string.give_input_kg);
        }
        else if(rate.equals(" ") || rate.equals("")){
            message = context.getResources().getString(R.string.give_input_rate);
        }
        return message;
    }
}
