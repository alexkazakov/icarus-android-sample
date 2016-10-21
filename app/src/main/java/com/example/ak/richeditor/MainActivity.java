package com.example.ak.richeditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.TextViewToolbar;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Html;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.popover.ImagePopoverImpl;
import com.github.mr5.icarus.popover.LinkPopoverImpl;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity{

    private Icarus mIcarus;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.wvEditor);

        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("Rich editor...");

        mIcarus = new Icarus(toolbar, options, webView);

        TextViewButton button = new TextViewButton((TextView) findViewById(R.id.btnBold), mIcarus);
        button.setName(Button.NAME_BOLD);
        toolbar.addButton(button);

        button = new TextViewButton((TextView) findViewById(R.id.btnItalic), mIcarus);
        button.setName(Button.NAME_ITALIC);
        toolbar.addButton(button);

        button = new TextViewButton((TextView) findViewById(R.id.btnUnderline), mIcarus);
        button.setName(Button.NAME_UNDERLINE);
        toolbar.addButton(button);

        TextView tvLink = (TextView) findViewById(R.id.btnLink);
        button = new TextViewButton(tvLink, mIcarus);
        button.setPopover(new LinkPopoverImpl(tvLink, mIcarus));
        button.setName(Button.NAME_LINK);
        toolbar.addButton(button);

        TextView tvImage = (TextView) findViewById(R.id.btnImage);
        button = new TextViewButton(tvImage, mIcarus);
        button.setPopover(new ImagePopoverImpl(tvImage, mIcarus));
        button.setName(Button.NAME_IMAGE);
        toolbar.addButton(button);

        mIcarus.setContent("<p>Hello!</p>");
        mIcarus.render();

        mIcarus.runAfterReady(new Runnable(){

            @Override
            public void run(){
                Log.d("APP", "Editor Ready");
            }
        });

        findViewById(R.id.btnPrint).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View aView){
                mIcarus.getContent(new Callback(){

                    @Override
                    public void run(String params){
                        Gson gson = new Gson();
                        Html html = gson.fromJson(params, Html.class);
                        Log.d("APP", "Content gotten: " + html.getContent());
                    }
                });
            }
        });

    }
}
