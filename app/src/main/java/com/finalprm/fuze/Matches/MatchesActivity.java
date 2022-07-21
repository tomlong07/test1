package com.finalprm.fuze.Matches;


public class MatchesActivity
//        extends AppCompatActivity
{
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mMatchesAdapter;
//    private RecyclerView.LayoutManager mMatchesLayoutManager;
//    private ImageButton mBack;
//    private DatabaseReference current;
//    private ValueEventListener listen;
//    private HashMap<String, Integer> mList = new HashMap<>();
//    private String cusrrentUserID, mLastTimeStamp, mLastMessage, lastSeen;
//    DatabaseReference mCurrUserIdInsideMatchConnections, mCheckLastSeen;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_matches);
//        bindingView();
//        bindingAction();
//        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setHasFixedSize(true);
//        mMatchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
//        mRecyclerView.setLayoutManager(mMatchesLayoutManager);
//        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), MatchesActivity.this);
//        mRecyclerView.setAdapter(mMatchesAdapter);
//        getUserMatchId();
//        mLastMessage = mLastTimeStamp = lastSeen = "";
//    }
//
//    private void bindingAction() {
//        mBack.setOnClickListener(this::onBackClick);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//    }
//
//    private void onBackClick(View view) {
//        Intent intent = new Intent(MatchesActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//        return;
//    }
//
//    private void bindingView() {
//        mBack = findViewById(R.id.matchesBack);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//    protected void onStop(){
//        super.onStop();
//    }
//
//    private void getLastMessageInfo(DatabaseReference userDb){
//        //chat id of the current match
//        mCurrUserIdInsideMatchConnections = userDb.child("connections").child("matches").child(cusrrentUserID);
//
//        mCurrUserIdInsideMatchConnections.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // lastSeen is actually lastsend and is true if other user sent me a message and I have not read it yet.
//                if (dataSnapshot.exists()){
//                    if(dataSnapshot.child("lastMessage").getValue() != null && dataSnapshot.child("lastTimeStamp")
//                            .getValue() != null && dataSnapshot.child("lastSend").getValue() != null) {
//                        mLastMessage = dataSnapshot.child("lastMessage").getValue().toString();
//                        mLastTimeStamp = dataSnapshot.child("lastTimeStamp").getValue().toString();
//                        lastSeen = dataSnapshot.child("lastSend").getValue().toString();
//                    }
//                    else{
//                        mLastMessage = "Start Chatting now!";
//                        mLastTimeStamp = " ";
//                        lastSeen = "true";
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getLastSeenInfo(DatabaseReference userDb, String key){
//        //chat id of the current match
//        mCheckLastSeen = userDb.child("connections").child("matches").child(key);
//
//        mCheckLastSeen.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    if(dataSnapshot.child("lastSeen").getValue() != null) {
//                        lastSeen = dataSnapshot.child("lastSeen").getValue().toString();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getUserMatchId() {
//        Query sortedMatchesByLastTimeStamp = FirebaseDatabase.getInstance()
//                .getReference().child("Users").child(cusrrentUserID).child("connections").child("matches")
//                .orderByChild("lastTimeStamp");
//
//        sortedMatchesByLastTimeStamp.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//
//                    for(DataSnapshot match : dataSnapshot.getChildren()){
//                        FetchMatchInformation(match.getKey(), match.child("ChatId").toString());
//                        //getChatID(match.child("ChatId").toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getChatID(String chatId) {
//        DatabaseReference mChatDB = FirebaseDatabase.getInstance().getReference().child("Chat").child(chatId).child("info");
//        mChatDB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    String chatId = "";
//
//                    if(dataSnapshot.child("id").getValue() != null)
//                        chatId = dataSnapshot.child("id").getValue().toString();
//
//
//
//                    for(DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren()){
//                        for(MatchesObject mChat : resultsMatches){
//                            if(mChat.getChatId().equals(chatId)){
//                                User mUser = new User(userSnapshot.getKey());
//                                mChat.addUserToArrayList(mUser);
//                                getUserData(mUser);
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void getUserData(User mUser) {
//        DatabaseReference mUserDb = FirebaseDatabase.getInstance().getReference().child("user").child(mUser.getUserId());
//        mUserDb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User mUser = new User(dataSnapshot.getKey());
//
//                if(dataSnapshot.child("notificationKey").getValue() != null)
//                    mUser.setNotificationKey(dataSnapshot.child("notificationKey").getValue().toString());
//
//                for(MatchesObject mChat : resultsMatches){
//                    for (User mUserIt : mChat.getUserObjectArrayList()){
//                        if(mUserIt.getUserId().equals(mUser.getUserId())){
//                            mUserIt.setNotificationKey(mUser.getNotificationKey());
//                        }
//                    }
//                }
////                mMatchesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void FetchMatchInformation(final String key, final String chatid) {
//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
//
//        getLastMessageInfo(userDb);
//
//        userDb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    String userId = dataSnapshot.getKey();
//                    String name = "";
//                    String profileImageUrl = "";
//                    String gender = "";
//                    String favorite = "";
//                    final String lastMessage = "";
//                    String lastTimeStamp = "";
//
//                    if(dataSnapshot.child("name").getValue()!=null){
//                        name = dataSnapshot.child("name").getValue().toString();
//                    }
//                    if(dataSnapshot.child("profileImageUrl").getValue()!=null){
//                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
//                    }
//                    if(dataSnapshot.child("gender").getValue() != null){
//                        gender = dataSnapshot.child("gender").getValue().toString();
//                    }
//                    if(dataSnapshot.child("favorite").getValue() != null){
//                        favorite = dataSnapshot.child("favorite").getValue().toString();
//                    }
//
//                    String milliSec = mLastTimeStamp;
//                    Long now;
//
//                    try {
//                        now = Long.parseLong(milliSec);
//                        lastTimeStamp = convertMilliToRelativeTime(now);
//                        String[] arrOfStr = lastTimeStamp.split(",");
//                        mLastTimeStamp = arrOfStr[0];
//                    } catch (Exception e) {}
//
//                    MatchesObject obj = new MatchesObject(userId, name, profileImageUrl,
//                            gender, favorite, mLastMessage, mLastTimeStamp, chatid, lastSeen);
//                    if(mList.containsKey(chatid)){
//                        int key = mList.get(chatid);
//                        resultsMatches.set(resultsMatches.size() - key, obj);
//
//                    }
//                    else {
//                        resultsMatches.add(0, obj);
//                        mList.put(chatid, resultsMatches.size());
//                    }
//                    mMatchesAdapter.notifyDataSetChanged();
//
//
////                    mLastMessage = "";
////                    mLastTimeStamp = "";
////                    lastSeen = "";
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//    public String convertMilliToRelativeTime(Long now) {
//        String time = DateUtils.getRelativeDateTimeString(this, now, DateUtils.SECOND_IN_MILLIS,
//                DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
//        return time;
//    }
//
//
//    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
//    private List<MatchesObject> getDataSetMatches() {
//        return resultsMatches;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        return;
//    }

}
