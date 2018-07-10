package asu.com.tryout.RecyclerStuff;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asu.com.tryout.R;
import asu.com.tryout.WeatherLog;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<WeatherLog> weatherLogs;
    final String DEGREE_F  = "\u2109";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView temp, mainW, press,humidity,speedW, country,city;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);

            temp = (TextView) view.findViewById(R.id.temp);
            mainW = (TextView) view.findViewById(R.id.mainW);
            press = (TextView) view.findViewById(R.id.pressure);
            humidity = (TextView) view.findViewById(R.id.humdidty);
            speedW = (TextView) view.findViewById(R.id.wind);
            country = (TextView) view.findViewById(R.id.Country);
            city = (TextView) view.findViewById(R.id.City);
            icon = (ImageView) view.findViewById(R.id.wicon);

        }
    }

    public WeatherAdapter(List<WeatherLog> weatherLogs) {
        this.weatherLogs = weatherLogs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeatherLog weather = weatherLogs.get(position);
        holder.temp.setText(Double.toString(weather.getTemperatureM())+DEGREE_F);
        holder.mainW.setText(weather.getMainW());
        holder.press.setText("Pr: "+Double.toString(weather.getPressureM())+" hPa");
        holder.humidity.setText("Hm: "+Double.toString(weather.getHumidityM())+"%");
        holder.speedW.setText("Wn: "+Double.toString(weather.getSpeedW())+" mps");
        holder.country.setText(weather.getCountryS());
        holder.city.setText(weather.getCity());
        int value = weather.getIconValue();
        if(value==0){
            holder.icon.setImageResource(R.drawable.clouds);
        }else if(value==1){
            holder.icon.setImageResource(R.drawable.sun);
        }else if(value==2){
            holder.icon.setImageResource(R.drawable.rain);
        }else if(value==3){
            holder.icon.setImageResource(R.drawable.snowflake);
        }else if(value==4){
            holder.icon.setImageResource(R.drawable.sun_cloudy);
        }


    }

    @Override
    public int getItemCount() {
        return weatherLogs.size();
    }
}
