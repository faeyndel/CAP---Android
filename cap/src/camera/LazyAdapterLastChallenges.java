package camera;


import java.util.ArrayList;

import com.esgi.cap.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 
public class LazyAdapterLastChallenges extends BaseAdapter {
     
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public ArrayList<String> imageUrls;
    public ArrayList<String> datas;
    public String[] imageUrlsData;
     
    public LazyAdapterLastChallenges(Activity a, String[] d) {
        activity = a;
        data=d;
        imageUrls = new ArrayList<String>();
        datas = new ArrayList<String>();
        int j=0;
        for (int i = 0; i < data.length; i++) {
        	if (data[i].toString().contains("http")){
        		imageUrls.add(data[i].toString());
        		j++;
        	}else{
        		datas.add(data[i].toString());
        	}
		}
        
        imageUrlsData = new String[imageUrls.size()];
        int i = 0;
        for (String s : imageUrls) {
        	imageUrlsData[i] = s.toString();
        	i++;
		}

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return imageUrlsData.length;
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.challenge_row, null);

        ImageView image=(ImageView)vi.findViewById(R.id.challengePicture);
        //TextView idChallenge=(TextView)vi.findViewById(R.id.idChallenge);
        TextView challengeName=(TextView)vi.findViewById(R.id.challengeName);
        TextView challengerName=(TextView)vi.findViewById(R.id.challengerName);
        
        int i = 0;
        int pos = 0;
        for (String s : datas) {
        	if(pos == position){
        		if(i == 0){ challengerName.setText(s); }
            	if(i == 1){ challengeName.setText(s); break; }
        	}
        	if(i == 1){
        		i = 0;
        		pos++;
        	}else{
            	i++;
        	}
		}
        
        imageLoader.DisplayImage(imageUrlsData[position], image);
        return vi;
    }
}