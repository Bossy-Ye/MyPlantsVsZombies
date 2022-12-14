package com.example.myplantsvszombies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.work.Data;

import com.example.myplantsvszombies.src.layer.LogoLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Button start;
    Button add;
    Button search;
    Button bind,unbind,getstatus;
    Button sendBroadcast;
    BindService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BindService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        start = (Button) findViewById(R.id.gameStart);
        add = (Button)findViewById(R.id.add);
        search=(Button) findViewById(R.id.search);
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        getstatus = (Button) findViewById(R.id.getstatus);
        sendBroadcast = (Button) findViewById(R.id.sendBroadcast);
        Activity activity = this;
        final Intent intent = new Intent(this,BindService.class);
        IntentFilter filter = new IntentFilter("org.crazyit.action.CRAZY_BROADCAST");
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver,filter);
        sendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setAction("org.crazyit.action.CRAZY_BROADCAST");
                intent.putExtra("msg","?????????????????????");
                sendBroadcast(intent);
            }
        });
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(conn);
            }
        });

        getstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "count = "+binder.getCount(),Toast.LENGTH_SHORT).show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                CCGLSurfaceView ccglSurfaceView=new CCGLSurfaceView(activity);
                activity.setContentView(ccglSurfaceView);
                CCDirector ccDirector = CCDirector.sharedDirector();
                ccDirector.attachInView(ccglSurfaceView);
                //fps
                ccDirector.setDisplayFPS(true);
                ccDirector.setScreenSize(Tools.screen_width,Tools.screen_height);//??????????????????
                CCScene ccScene= CCScene.node();
                ccScene.addChild(new LogoLayer());
                ccDirector.runWithScene(ccScene);
            }
        });

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View source)
            {
                // ????????????List?????????????????????????????????????????????????????????????????????Email?????????
                final ArrayList<String> names = new ArrayList<String>();
                final ArrayList<ArrayList<String>> details
                        = new ArrayList<ArrayList<String>>();
                // ??????ContentResolver?????????????????????
                Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        , null, null, null, null);
                // ???????????????????????????????????????????????????
                while (cursor.moveToNext())
                {
                    // ???????????????ID
                    @SuppressLint("Range") String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    // ????????????????????????
                    @SuppressLint("Range") String name = cursor.getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    names.add(name);
                    // ??????ContentResolver??????????????????????????????
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    ArrayList<String> detail = new ArrayList<String>();
                    // ????????????????????????????????????????????????????????????
                    while (phones.moveToNext())
                    {
                        // ????????????????????????????????????????????????
                        @SuppressLint("Range") String phoneNumber = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract
                                                .CommonDataKinds.Phone.NUMBER));
                        detail.add("???????????????" + phoneNumber);
                    }
                    phones.close();
                    // ??????ContentResolver??????????????????Email??????
                    Cursor emails = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                    + " = " + contactId, null, null);
                    // ????????????????????????????????????????????????Email??????
                    while (emails.moveToNext())
                    {
                        // ?????????????????????Email?????????????????????
                        @SuppressLint("Range") String emailAddress = emails
                                .getString(emails
                                        .getColumnIndex(ContactsContract
                                                .CommonDataKinds.Email.DATA));
                        detail.add("???????????????" + emailAddress);
                    }
                    emails.close();
                    details.add(detail);
                }
                cursor.close();
                //??????result.xml???????????????????????????
                View resultDialog = getLayoutInflater().inflate(
                        R.layout.activity_result, null);
                // ??????resultDialog???ID???list???ExpandableListView
                ExpandableListView list = (ExpandableListView)resultDialog
                        .findViewById(R.id.list);
                //????????????ExpandableListAdapter??????
                ExpandableListAdapter adapter = new BaseExpandableListAdapter()
                {
                    //??????????????????????????????????????????????????????????????????
                    @Override
                    public Object getChild(int groupPosition, int childPosition)
                    {
                        return details.get(groupPosition).get(childPosition);
                    }
                    @Override
                    public long getChildId(int groupPosition, int childPosition)
                    {
                        return childPosition;
                    }
                    @Override
                    public int getChildrenCount(int groupPosition)
                    {
                        return details.get(groupPosition).size();
                    }
                    private TextView getTextView()
                    {
                        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                                ViewGroup.LayoutParams.FILL_PARENT, 64);
                        TextView textView = new TextView(MainActivity.this);
                        textView.setLayoutParams(lp);
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                        textView.setPadding(36, 0, 0, 0);
                        textView.setTextSize(20);
                        return textView;
                    }
                    // ???????????????????????????????????????
                    @Override
                    public View getChildView(int groupPosition, int childPosition,
                                             boolean isLastChild, View convertView, ViewGroup parent)
                    {
                        TextView textView = getTextView();
                        textView.setText(getChild(groupPosition, childPosition).toString());
                        return textView;
                    }
                    //????????????????????????????????????
                    @Override
                    public Object getGroup(int groupPosition)
                    {
                        return names.get(groupPosition);
                    }
                    @Override
                    public int getGroupCount()
                    {
                        return names.size();
                    }
                    @Override
                    public long getGroupId(int groupPosition)
                    {
                        return groupPosition;
                    }
                    //???????????????????????????????????????
                    @Override
                    public View getGroupView(int groupPosition, boolean isExpanded,
                                             View convertView, ViewGroup parent)
                    {
                        TextView textView = getTextView();
                        textView.setText(getGroup(groupPosition).toString());
                        return textView;
                    }
                    @Override
                    public boolean isChildSelectable(int groupPosition, int childPosition)
                    {
                        return true;
                    }
                    @Override
                    public boolean hasStableIds()
                    {
                        return true;
                    }
                };
                // ???ExpandableListView??????Adapter??????
                list.setAdapter(adapter);
                // ???????????????????????????????????????
                new AlertDialog.Builder(MainActivity.this)
                        .setView(resultDialog)
                        .setPositiveButton("??????" , null)
                        .show();
            }
        });

        // ???add????????????????????????????????????
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // ????????????????????????3????????????
                String name = ((EditText)findViewById(R.id.name))
                        .getText().toString();
                String phone = ((EditText)findViewById(R.id.phone))
                        .getText().toString();
                String email = ((EditText)findViewById(R.id.email))
                        .getText().toString();
                // ??????????????????ContentValues
                ContentValues values = new ContentValues();
                // ???RawContacts.CONTENT_URI???????????????????????????
                // ??????????????????????????????rawContactId
                Uri rawContactUri = getContentResolver()
                        .insert(ContactsContract.RawContacts.CONTENT_URI, values);
                long rawContactId = ContentUris.parseId(rawContactUri);
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                // ??????????????????
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                // ?????????????????????
                values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
                // ????????????URI?????????????????????
                getContentResolver().insert(
                        android.provider.ContactsContract.Data.CONTENT_URI, values);
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                // ??????????????????????????????
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
                // ??????????????????
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                // ????????????????????????URI??????????????????
                getContentResolver().insert(
                        android.provider.ContactsContract.Data.CONTENT_URI, values);
                values.clear();
                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
                // ??????????????????Email??????
                values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
                // ??????????????????????????????
                values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                // ????????????Email URI??????Email??????
                getContentResolver().insert(
                        android.provider.ContactsContract.Data.CONTENT_URI, values);
                Toast.makeText(MainActivity.this
                        , "???????????????????????????" , Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}