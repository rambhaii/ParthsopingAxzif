package com.app.axzif.Search.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Locale;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Products.ui.ProductAdapterFull;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.UtilMethods;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText et_search;
     ProgressBar loadingPB;
   ImageView back_btn;
   NestedScrollView nestedScroll;
   ImageView speech,img_search;
    int page = 0, limit = 2;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       Getid();
    }

    private void Getid() {

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setTitle("Search");
//        toolbar.setTitleTextColor(Color.WHITE);
//
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,"",null);
        recyclerView=findViewById(R.id.recyclerView);
        et_search=findViewById(R.id.et_search);
        back_btn=findViewById(R.id.back_btn);
        loadingPB=findViewById(R.id.idPBLoading);
        nestedScroll=findViewById(R.id.nestedScroll);
        speech=findViewById(R.id.speech);
        img_search=findViewById(R.id.img_search);
        speech.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    if (intent.resolveActivity(SearchActivity.this.getPackageManager()) != null) {
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                } else {
                    Toast.makeText(SearchActivity.this, "Your device don't support speech input ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
     //   et_search.requestFocus();
       /* et_search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if(charSequence.length()==0)
                {
                    UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,charSequence+"",null);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>3)
                {
                    UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,charSequence+"",null);
                }
               ////Toast.makeText(SearchActivity.this, ""+charSequence, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });*/


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,et_search.getText().toString(),null);
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,et_search.getText().toString(),null);
                    performSearch();
                    return true;

                }
                return false;
            }
        });

    }
    private void performSearch()
    {
        et_search.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(et_search.getWindowToken(), 0);

        Log.d("dfjgjdfh",""+in);



    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        MenuItem searchViewItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchView.clearFocus();
//
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                Log.e("newText","newText :  "+newText);
//
//                UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,newText.trim(),null);
//
//               // adapter.getFilter().filter(newText);
//
//
//
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {   super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 10:
                if (resultCode == RESULT_OK && data!=null)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String d=result.get(0);

                   et_search.setText(result.get(0));
                    UtilMethods.INSTANCE.liveSearchByName(SearchActivity.this,result.get(0).toString(),null);

                    //  mList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, matches));
                }
        }

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("products_detail")){
            dataParse(""+ fragmentActivityMessage.getMessage());
        }
    }

    ProductAdapterFull mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    public void dataParse(String respose)
    {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapter = new ProductAdapterFull(transactionsObjects, this,"main");
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else
        {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "Data not found ", Toast.LENGTH_SHORT).show();
        }

    }





    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

}


