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
    int clicktime=0;
    Boolean finish=Boolean.FALSE;
    int[] space=new int[100]; //to decide if it is a bomb
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //random for bombs
        for (int i=0;i<100;i++){
            if (Math.random()>=0.33){//random if it is a bomb for the opportunity of 1/3 is bombs (you really need some luck to win the game XDD)
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
        buttonOnClick=(Button)findViewById(view.getId()); //find out which button is pressed
        if(finish==Boolean.FALSE && (buttonOnClick.getText().toString().equals("X") || buttonOnClick.getText().toString().equals(""))){
            //it will only work if the game is not finish and when you are correcting an "X" or you have not press it yet
            idget=getResources().getResourceEntryName(view.getId()); //get the id of the button
            splitid = idget.split("n");//split the id by "n"
            numberchoose=Integer.parseInt(splitid[1])-1;//the [1] is the number of button, -1 for starting form 0
            checkBox=(CheckBox)findViewById(R.id.checkBox);
            if(checkBox.isChecked()){
                if (space[numberchoose]==1 && buttonOnClick.getText()!="X") {//this means that you find a bomb correctly
                    clicktime += 1;//get one point
                }
                buttonOnClick.setText("X");//sign X if you think it is a bomb
            }else{
                if(space[numberchoose]==1){
                    //if it is a bomb, explode
                    for(int i=0;i<100;i++){//show all bombs
                        if(space[i]==1){
                            buttonOnClick=(Button)findViewById(getResources().getIdentifier("Button" + (i+1),"id",getPackageName()));
                            buttonOnClick.setText("*");
                        }
                    }
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
                    clicktime+=1;//get one point for correctly find there is no bombs here
                }
            }
        }
        if (clicktime==100){//you win
            textView=(TextView)findViewById(R.id.textView);
            textView.setText("You Win!!");
            finish=Boolean.TRUE;
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
        clicktime=0;
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
