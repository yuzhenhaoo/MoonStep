package priv.zxy.moonstep.commerce.view.Tree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Tree.PetActivity;

public class TreeActivity extends Fragment {

    private Button petBt;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage1, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        petBt = (Button) view.findViewById(R.id.petBt);
    }

    private void initData(){

    }

    private void initEvent(){
        petBt.setOnClickListener(v -> toPetActivity());
    }

    private void toPetActivity(){
        Intent intent = new Intent(this.getContext(), PetActivity.class);
        startActivity(intent);
    }
}
