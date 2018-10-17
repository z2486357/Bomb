package com.example.aloha.bomb;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button buttonOnClick;
    TextView textView;
    CheckBox checkBox;
    Boolean finish=Boolean.FALSE;
    int[] space=new int[100]; //to decide if it is a bomb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //random for bombs
        for (int i=0;i<100;i++){
            if (Math.random()>=0.33){
                space[i]=0;
            }else {
                space[i]=1;
            }
        }
    }

    public void ButtonClick(View view) {
        int bombnumber=0;
        int numberchoose;
        String[] splitid;
        String idget;
        if(finish==Boolean.FALSE){
            buttonOnClick=(Button)findViewById(view.getId()); //find out which button is pressed
            idget=getResources().getResourceEntryName(view.getId()); //get the id of the button
            splitid = idget.split("n");//split the id by "n"
            numberchoose=Integer.parseInt(splitid[1])-1;//the [1] is the number of button, -1 for starting form 0
            checkBox=(CheckBox)findViewById(R.id.checkBox);
            if(checkBox.isChecked()){
                buttonOnClick.setText("X");//sign X if you think it is a bomb
            }else{
                if(space[numberchoose]==1){
                    buttonOnClick.setText("*");//if it is a bomb, explode
                    textView=(TextView)findViewById(R.id.textView);
                    textView.setText("Game Over");
                    finish=Boolean.TRUE;
                }else{
                    if(numberchoose>9){
                        bombnumber+=space[numberchoose-10];//find if there is a bomb top
                    }
                    if(numberchoose%10!=0){
                        bombnumber+=space[numberchoose-1];//find if there is a bomb left
                    }
                    if(numberchoose%10!=9){
                        bombnumber+=space[numberchoose+1];//find if there is a bomb right
                    }
                    if(numberchoose<90){
                        bombnumber+=space[numberchoose+10];//find if there is a bomb down
                    }
                    if(numberchoose>9 && numberchoose%10!=0){
                        bombnumber+=space[numberchoose-11];//find if there is a bomb top-left
                    }
                    if(numberchoose>9 && numberchoose%10!=9){
                        bombnumber+=space[numberchoose-9];//find if there is a bomb top-right
                    }
                    if(numberchoose<90 && numberchoose%10!=0){
                        bombnumber+=space[numberchoose+9];//find if there is a bomb down-left
                    }
                    if(numberchoose<90 && numberchoose%10!=9){
                        bombnumber+=space[numberchoose+11];//find if there is a bomb down-right
                    }
                    buttonOnClick.setText(String.valueOf(bombnumber));//show the bombs number next to it
                }
            }

        }

        //textView=(TextView)findViewById(R.id.textView);
        //textView.setText(String.valueOf(numberchoose));
    }

    public void ButtonRestartClick(View view) {
        for(int i=1;i<101;i++){//clear all block
            buttonOnClick=(Button)findViewById(getResources().getIdentifier("Button" + i,"id",getPackageName()));
            buttonOnClick.setText("");
        }
        finish=Boolean.FALSE;
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("");
        checkBox.setChecked(false);
        //random for bombs
        for (int i=0;i<100;i++){
            if (Math.random()>=0.33){
                space[i]=0;
            }else {
                space[i]=1;
            }
        }
    }
}
