package com.digitalhouse.bnews.modules.noticia.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhouse.bnews.R;
import com.digitalhouse.bnews.listener.NoticiaListener;
import com.digitalhouse.bnews.model.Noticia;
import com.digitalhouse.bnews.modules.detalheNoticia.view.DetalheNoticiaActivity;
import com.digitalhouse.bnews.modules.noticia.viewmodel.NoticiaViewModel;
import com.digitalhouse.bnews.repository.NoticiaAdapter;

import java.util.ArrayList;

public class NoticiaActivity extends AppCompatActivity implements NoticiaListener {

    //private ProgressBar progressBar;
    private RecyclerView noticiaRecyclerView;
    private String search = "covid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ArrayList<Noticia> noticiaArrayList = new ArrayList<>();

        noticiaRecyclerView = findViewById(R.id.noticia_recycler_view);
        //progressBar = findViewById(R.id.progress_bar_noticia);
        NoticiaAdapter noticiaAdapter = new NoticiaAdapter(noticiaArrayList, this);


        noticiaRecyclerView.setAdapter(noticiaAdapter);
        noticiaRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoticiaViewModel noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);

        noticiaViewModel.atualizarNoticias(search);


        noticiaViewModel.getNoticiaLiveData()
                .observe(this, noticiaList -> {
                    noticiaAdapter.adicionarNoticia(noticiaList);
                });

    }

    @Override
    public void onNoticiaClick(Noticia noticia) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("NOTICIA", noticia);

        Intent intent = new Intent(this, DetalheNoticiaActivity.class);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void compartilharNoticia(Noticia noticia) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, noticia.getTitle());
        intent.putExtra(Intent.EXTRA_SUBJECT, noticia.getUrl());
        startActivity(Intent.createChooser(intent, "Compartilhar"));

    }
}
