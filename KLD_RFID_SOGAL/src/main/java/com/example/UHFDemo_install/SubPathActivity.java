package com.example.UHFDemo_install;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.uhf.uhf.UHF1.UHF1Application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kld.com.rfid.ldw.R;

public class SubPathActivity extends Activity {
	 static private int openfileDialogId = 0; 
	Button button_selectfile;
	ListView listview;
	public static String tag = "SaveFileDialog";  
   static final public String sRoot = "/";   
   static final public String sParent = "..";  
   static final public String sFolder = ".";  
   static final public String sEmpty = "";  
   static final private String sOnErrorMsg = "No rights to access!";  
 
   private String path = sRoot;  
   private List<Map<String, Object>> list = null;
   private Map<String, Integer> imagemap = new HashMap<String, Integer>(); ;  
   
   UHF1Application myapp;
   
   private int getImageId(String s){  
       if(imagemap == null){  
           return 0;  
      }  
      else if(imagemap.containsKey(s)){  
           return imagemap.get(s);  
      }  
       else if(imagemap.containsKey(sEmpty)){  
           return imagemap.get(sEmpty);  
       }  
       else {  
           return 0;  
       }  
  }  
   private String getSuffix(String filename){  
       int dix = filename.lastIndexOf('.');  
       if(dix<0){  
           return "";  
       }  
       else{  
          return filename.substring(dix+1);  
       }  
  }  
   private int refreshFileList()  
   {  
       // 刷新文件列表   
        File[] files = null;  
        try{  
           files = new File(path).listFiles();  
       }  
        catch(Exception e){  
            files = null;  
        }  
        if(files==null){  
           // 访问出错   
            Toast.makeText(this, sOnErrorMsg,Toast.LENGTH_SHORT).show();  
           return -1;  
       }  
       if(list != null){  
            list.clear();  
        }  
        else{  
           list = new ArrayList<Map<String, Object>>(files.length);  
        }  
         
      // 用来先保存文件夹和文件夹的两个列表   
       ArrayList<Map<String, Object>> lfolders = new ArrayList<Map<String, Object>>();  
       ArrayList<Map<String, Object>> lfiles = new ArrayList<Map<String, Object>>();  
         
       if(!this.path.equals(sRoot)){  
           // 添加根目录 和 上一层目录   
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("name", sRoot);  
            map.put("path", sRoot);  
            map.put("img", getImageId(sRoot));  
            list.add(map);  
              
           map = new HashMap<String, Object>();  
           map.put("name", sParent);  
           map.put("path", path);  
            map.put("img", getImageId(sParent));  
            list.add(map);  
        }  
       String suffix=null;//".bin;"
       for(File file: files)  
       {  
           if(file.isDirectory() && file.listFiles()!=null){  
                // 添加文件夹   
               Map<String, Object> map = new HashMap<String, Object>();  
               map.put("name", file.getName());  
                map.put("path", file.getPath());  
               map.put("img", getImageId(sFolder));  
                lfolders.add(map);  
            }  
            else if(file.isFile()){  
                // 添加文件   
               String sf = getSuffix(file.getName()).toLowerCase();  
               if(suffix == null || suffix.length()==0 || (sf.length()>0 && suffix.indexOf("."+sf+";")>=0)){  
                    Map<String, Object> map = new HashMap<String, Object>();  
                    map.put("name", file.getName());  
                    map.put("path", file.getPath());  
                    map.put("img", getImageId(sf));  
                    lfiles.add(map);  
                }  
            } 
             
        }  
         
       list.addAll(lfolders); // 先添加文件夹，确保文件夹显示在上面   
       list.addAll(lfiles);    //再添加文件   
          
          
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.filedialogitem, new String[]{"img", "name", "path"}, new int[]{R.id.filedialogitem_img, R.id.filedialogitem_name, R.id.filedialogitem_path});
        listview.setAdapter(adapter);  
        return files.length;  
    }  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.path_location);
		Application app=getApplication();
		myapp=(UHF1Application) app;
		 
		 // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹  

        imagemap.put(sRoot, R.drawable.filedialog_root);   // 根目录图标
		imagemap.put(sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标   
		imagemap.put(sFolder, R.drawable.filedialog_folder);   //文件夹图标   
		imagemap.put("bin", R.drawable.filedialog_file);  
      //images.put("wav", R.drawable.filedialog_wavfile);   //wav文件图标   //wav文件图标   
		imagemap.put(sEmpty, R.drawable.filedialog_root);  
        
        
		listview=(ListView)this.findViewById(R.id.listView_list);

		button_selectfile=(Button)this.findViewById(R.id.button_select);
		button_selectfile.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(SubPathActivity.this, path,
						Toast.LENGTH_SHORT).show();
				SubSystemActivity.et_path.setText(path);
				finish();
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				 // 条目选择   
	             String pt = (String) list.get(arg2).get("path");  
	            String fn = (String) list.get(arg2).get("name");  
	             if(fn.equals(sRoot) || fn.equals(sParent)){  
	                 // 如果是更目录或者上一层   
	                 File fl = new File(pt);  
	                String ppt = fl.getParent();  
	                 if(ppt != null){  
	                     // 返回上一层   
	                     path = ppt;  
	                 }  
	                else{  
	                     // 返回更目录   
	                     path = sRoot;  
	                 }  
	             }  
	           else{  
	                File fl = new File(pt);  
	               if(fl.isFile()){  
	                   // 如果是文件   
	                  
	                    return;  
	               }  
	               else if(fl.isDirectory()){  
	                   // 如果是文件夹   
	                    // 那么进入选中的文件夹   
	                   path = pt;  
	                   
	                }  
	           }  
	            refreshFileList();
			}
		});
		
		 refreshFileList();  
	}
 
}
