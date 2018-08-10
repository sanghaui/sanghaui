package com.example.home.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.home.sample.Board.BoardTouchActivity;
import com.example.home.sample.Board.BoardWriter;
import com.example.home.sample.Board.ModiDel;
import com.example.home.sample.Board.SangWaDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BoardFragment extends Fragment {

    Button button1;
    ListView boardList;
    BoardAdapter adapter;
    ArrayList<SangWaDTO> dtolist = new ArrayList<>();
    MainActivity activity;
    Spinner b_spinner;
    ArrayAdapter<CharSequence> spinnerAdapter;
    String searchTextresult;
    EditText searchText;
    DBconnectionDeleter deleter;
    SangWaDTO dto;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup boardfrag = (ViewGroup) inflater.inflate(R.layout.boardfragment, container, false);


        getList();


        boardList = boardfrag.findViewById(R.id.boardList);

        button1 = boardfrag.findViewById(R.id.button1);


        //글쓰기 버튼
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writer = new Intent(getContext(), BoardWriter.class);
                startActivity(writer);


            }
        });

        //스피너 설정
        String[] b_list = {"제목", "작성자", "내용"};
        b_spinner = boardfrag.findViewById(R.id.b_spinner);
        spinnerAdapter= new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item,b_list);
        b_spinner.setAdapter(spinnerAdapter);
        b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("스피너선택값",b_spinner.getSelectedItem().toString());
                searchTextresult=b_spinner.getSelectedItem().toString();
                if(searchTextresult.equals("제목")){
                    adapter.insertSearch("title");
                }else if(searchTextresult.equals("작성자")){
                    adapter.insertSearch("id");
                }else if(searchTextresult.equals("내용")){
                    adapter.insertSearch("content");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //리스트 할당
        boardList = boardfrag.findViewById(R.id.boardList);
        /*adapter = (BoardAdapter) boardList.getAdapter();*/
        boardList.setAdapter(adapter);


        searchText = boardfrag.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable search) {
                //검색어 변경시 처리
                String filterText = search.toString();
                Log.d("검색어 길이",filterText);
                if(filterText.length()>0){
                    boardList.setFilterText(filterText);
                }else{
                    boardList.clearTextFilter();
                }
            }
        });

        //리스트 선택 처리
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SangWaDTO dto = (SangWaDTO) adapter.getItem(position);
                Intent boardTouch = new Intent(getContext(), BoardTouchActivity.class);
                boardTouch.putExtra("tag", "touch");
                boardTouch.putExtra("id", dto.getId());
                boardTouch.putExtra("content", dto.getContent());
                boardTouch.putExtra("title", dto.getTitle());
                boardTouch.putExtra("date", dto.getDate());
                boardTouch.putExtra("imgRes", dto.getImgRes());
                boardTouch.putExtra("index", dto.getIndex());
                startActivity(boardTouch);
            }
        });

        //리스트 선택 삭제 처리
        boardList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleter = new DBconnectionDeleter();
                dto = (SangWaDTO) adapter.getItem(position);

                Intent intent = new Intent(getContext(),ModiDel.class);
                intent.putExtra("index",dto.getIndex());
                intent.putExtra("id",dto.getId());
                intent.putExtra("title",dto.getTitle());
                intent.putExtra("content",dto.getContent());
                intent.putExtra("imgRes",dto.getImgRes());
                startActivity(intent);
                return true;
            }
        });



        boardList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return false;
            }
        });



        return boardfrag;
    }

    public void getList() {
        //DB에서 값 받아옴
        DBconnectionreader reader = new DBconnectionreader();

        try {
            dtolist = reader.execute().get();
            Log.d("반환값", String.valueOf(dtolist.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //adapter 생성
        adapter = new BoardAdapter();

        //배열을 풀어 각각에 리스트에 삽입
        for (int i = 0; i < dtolist.size(); i++) {
            if (dtolist.get(i).getId().equals("반갑습니다")){

            }else {
                String id = dtolist.get(i).getId();
                String title = dtolist.get(i).getTitle();
                String date = dtolist.get(i).getDate();
                String img = dtolist.get(i).getImgRes();
                String content = dtolist.get(i).getContent();
                int index = dtolist.get(i).getIndex();
                int like = dtolist.get(i).getLike();
                Log.d("게시판글", "아이디:" + id + ",제목:" + title + ",시간:" + date);
                adapter.addItems(new SangWaDTO(id, title, date, img, content, index, like));
            }
        }

    }

    //프래그먼트 갱신
    public void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

    }
}
