package com.example.user.sangwa_test.Board;

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
import android.widget.Toast;

import com.example.user.sangwa_test.Board.Adapters.BoardAdapter;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.DBconnectionDeleter;
import com.example.user.sangwa_test.DBconnectionreader;
import com.example.user.sangwa_test.MainActivity;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BoardFragment extends Fragment {

    String userId;


    Button writeButton;
    BoardAdapter adapter;
    ListView boardList;
    ArrayList<SangWaDTO> dtolist = new ArrayList<>();
    Context context;
    SangWaDTO dto;
    DBconnectionDeleter deleter;
    Spinner b_spinner;
    EditText searchText;
    String searchTextresult;
    ArrayAdapter<CharSequence> spinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup board = (ViewGroup) inflater.inflate(R.layout.board_fragment, container, false);
        /*MainActivity activity = (MainActivity) getActivity();*/
        /*context =activity.getApplicationContext();*/
        userId = "test";
        getList();
        //리스트 검색


        //스피너 설정
        String[] b_list = {"제목", "작성자", "내용"};
        b_spinner = board.findViewById(R.id.b_spinner);

        spinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, b_list);
        b_spinner.setAdapter(spinnerAdapter);
        b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("스피너선택값", b_spinner.getSelectedItem().toString());
                searchTextresult = b_spinner.getSelectedItem().toString();
                if (searchTextresult.equals("제목")) {
                    adapter.insertSearch("title");
                } else if (searchTextresult.equals("작성자")) {
                    adapter.insertSearch("id");
                } else if (searchTextresult.equals("내용")) {
                    adapter.insertSearch("content");
                } else {
                    adapter.insertSearch("title");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //리스트 할당
        boardList = board.findViewById(R.id.boardList);
        boardList.setAdapter(adapter);


        searchText = board.findViewById(R.id.searchText);
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
                Log.d("검색어 길이", filterText);
                if (filterText.length() > 0) {
                    boardList.setFilterText(filterText);
                } else {
                    boardList.clearTextFilter();
                }
            }
        });

        //리스트 선택 처리
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dto = (SangWaDTO) adapter.getItem(position);
                Intent boardTouch = new Intent(getContext(), BoardTouchActivity.class);
                /*boardTouch.putExtra("tag", "touch");*/
                int index = dto.getIndex();
                boardTouch.putExtra("index", index);
                boardTouch.putExtra("id", userId);
                boardTouch.putExtra("pw", dto.getPw());
                boardTouch.putExtra("content", dto.getContent());
                boardTouch.putExtra("title", dto.getTitle());
                boardTouch.putExtra("date", dto.getDate());
                boardTouch.putExtra("imgRes", dto.getImgRes());
                boardTouch.putExtra("readCount", dto.getReadCount());
                startActivity(boardTouch);

            }
        });

        //리스트 선택 삭제 처리
        boardList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleter = new DBconnectionDeleter();
                dto = (SangWaDTO) adapter.getItem(position);
                if (!userId.equals(dto.getId())) {
                    Toast.makeText(getContext(), "작성자 아이디가 일치 하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), ModiDel.class);
                    intent.putExtra("index", dto.getIndex());
                    intent.putExtra("id", userId);
                    intent.putExtra("title", dto.getTitle());
                    intent.putExtra("content", dto.getContent());
                    intent.putExtra("imgRes", dto.getImgRes());
                    startActivity(intent);
                }
                return true;
            }
        });

        //글쓰기버튼
        writeButton = board.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writer = new Intent(getContext(), BoardWriter.class);
                writer.putExtra("id", userId);
                startActivity(writer);
            }
        });
        return board;
    }

    @Override
    public void onResume() {
        getList();
        boardList.setAdapter(adapter);
        searchText.setText("");
        b_spinner.setSelection(0);
        super.onResume();
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
        Log.d("어뎁터", "생성");

        //배열을 풀어 각각에 리스트에 삽입
        for (int i = 0; i < dtolist.size(); i++) {
            int index = dtolist.get(i).getIndex();
            String id = dtolist.get(i).getId();
            String title = dtolist.get(i).getTitle();
            String date = dtolist.get(i).getDate();
            String content = dtolist.get(i).getContent();
            String reply = dtolist.get(i).getReply();
            int like = dtolist.get(i).getLike();
            int readCount = dtolist.get(i).getReadCount();
            String imgRes = dtolist.get(i).getImgRes();
            Log.d("게시판글", "인덱스:" + index + ",조회수:" + readCount + ",시간:" + date);
            adapter.addItems(new SangWaDTO(index, id, title, content, date, reply, like, readCount, imgRes));
        }
    }

    //프래그먼트 갱신
    public void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

    }
}

