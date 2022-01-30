package com.example.sqlslider;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ConnectionClass connectionClass;
    private Connection connection;
    private SliderView sliderView;
    private SliderAdapterExample adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        connection = connectionClass.connection();

        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        renewItems(sliderView);
    }
    public void renewItems(View view){
        List<SliderItem> sliderItemList = new ArrayList<>();
        if(connection != null){
            String query = "SELECT name,url FROM table1";
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    String url = resultSet.getString("url");
                    String name = resultSet.getString("name");
                    SliderItem sliderItem = new SliderItem();
                    sliderItem.setDescription(name);
                    sliderItem.setImageUrl(url);
                    sliderItemList.add(sliderItem);
                    adapter.renewItems(sliderItemList);
                }
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
    }

}