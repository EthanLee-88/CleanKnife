package com.example.cleanknife.CleanKnife;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CleanBinder {

    public static void bind(AppCompatActivity appCompatActivity){
        bindView(new ViewFinder(appCompatActivity), appCompatActivity);
        bindMethod(new ViewFinder(appCompatActivity), appCompatActivity);
    }
    public static void bind(Activity activity){
        bindView(new ViewFinder(activity), activity);
        bindMethod(new ViewFinder(activity), activity);
    }

    /**
     * 兼容
     *
     * @param view layout
     * @param object fragment
     */
    public static void bind(View view, Object object){
        bindView(new ViewFinder(view), object);
        bindMethod(new ViewFinder(view), object);
    }

    /**
     * 绑定 View
     *
     * @param object
     */
    private static void bindView(ViewFinder viewFinder, Object object){
        if (object == null) return;
        Class<?> clazz = object.getClass();
        // 反射获取对象里所有属性
        Field [] fields = clazz.getDeclaredFields();
        // 遍历所有属性，找到添加了 FindView注解的，然后绑定
        for (Field field : fields){
            FindView findView = field.getAnnotation(FindView.class);
            if (findView != null){
                // 1 拿到注解里声明的 id
                int viewId = findView.value();
                // 2 根据 id 获取到 View
                View view = viewFinder.findViewById(viewId);
                // 设置属性可访问，包括私有的
                field.setAccessible(true);
                // 3 再将 View与属性绑定
                try {
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    Log.d("CleanBinder", "IllegalAccessException - " + field.getName().toString());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绑定点击事件注解
     *
     * @param object
     */
    private static void bindMethod(ViewFinder viewFinder, Object object){
        if (object == null) return;
        Class<?> clazz = object.getClass();
        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        // 遍历所有方法 找到有注解的
        for (Method method : methods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick == null) continue;
            // 1 拿注解里声明的 ID
            int viewID = onClick.value();
            // 2 再拿 View
            View view = viewFinder.findViewById(viewID);
            // 3 监听
            view.setOnClickListener((View  view1) -> {
                CheckNet checkNet = method.getAnnotation(CheckNet.class);
                if ((checkNet != null) && (!isNetAvailable((Context) object))){
                    Toast.makeText((Context) object, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 访问权
                method.setAccessible(true);
                try {
                    // 调用点击事件绑定的方法
                    method.invoke(object);
                } catch (IllegalAccessException e) {
                    Log.d("CleanBinder", "IllegalAccessException - " + method.getName());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Log.d("CleanBinder", "InvocationTargetException - " + method.getName());
                    e.printStackTrace();
                }
            });
        }
    }

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}
