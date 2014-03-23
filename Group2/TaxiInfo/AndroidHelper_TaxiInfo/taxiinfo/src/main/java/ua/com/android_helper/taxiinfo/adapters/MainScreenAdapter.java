package ua.com.android_helper.taxiinfo.adapters;

/**
 * Created with IntelliJ IDEA.
 * User: andriistakhov
 * Date: 26.09.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public class MainScreenAdapter {//extends CursorAdapter {

//    private final LayoutInflater inflator;
//    private static final int TYPE_MAX_COUNT = ItemType.values().length;
//    private final Typeface _typeface;
//    private final Context _context;
//
//
//    public MainScreenAdapter(Context context, Cursor c) {
//        super(context, c);
//        inflator = LayoutInflater.from(context);
//        _typeface = Typeface.createFromAsset(context.getAssets(), "font/Adventure.ttf");
//        _context = context;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getItem(position).getItemType().getCode();
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return TYPE_MAX_COUNT;
//    }
//
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//        ItemType itemType = ItemType.parseFromCode(cursor.getInt(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_ITEM_TYPE)));
//        View view = null;
//        ViewHolder viewHolder = new ViewHolder();
//
//
//        switch (itemType) {
//            case Video:
//
//                final ContentResolver resolver = _context.getContentResolver();
//                Cursor videoCursor = resolver.query(SQLiteContract.Video.CONTENT_URI, null, SQLiteContract.Video.COLUMN_VIDEO_ID + " = ?", new String[]{String.valueOf(cursor.getInt(0))}, null);
//
//                VideoData videoData = VideoDataAccess.parseDataFromCursor(videoCursor);
//
//                view = inflator.inflate(R.layout.lits_item_video, viewGroup, false);
//                viewHolder.downloadVideo = (ImageButton) view.findViewById(R.id.download_video);
//                viewHolder.downloadVideo.setTag(videoData);
//                viewHolder.downloadVideo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        VideoData videoData = (VideoData) v.getTag();
//
//                        if (videoData.isPaid()) {
//                            Intent intent = new Intent(_context, StripService.class);
//                            intent.putExtra(Constants.VIDEO_KEY, videoData);
//                            _context.startService(intent);
//                        } else {
//                            _context.startActivity(PaidActivity.getIntent(_context, String.valueOf(videoData.getId())));
//                        }
//
//                    }
//                });
//                viewHolder.playVideo = (ImageButton) view.findViewById(R.id.play_video);
//                viewHolder.playVideo.setTag(videoData);
//                viewHolder.playVideo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        VideoData videoData = (VideoData) v.getTag();
//                        Intent intent = VideoActivity.getItnent(_context, videoData.getFilePath());
//                        _context.startActivity(intent);
//                    }
//                });
//                viewHolder.removeVideo = (ImageButton) view.findViewById(R.id.remove_video);
//                viewHolder.removeVideo.setTag(videoData);
//                viewHolder.removeVideo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        VideoData videoData = (VideoData) v.getTag();
//
//                        File file = new File(videoData.getFilePath());
//                        file.delete();
//
//                        ContentValues values = new ContentValues();
//                        values.put(SQLiteContract.Video.COLUMN_VIDEO_PATH, "");
//                        resolver.update(SQLiteContract.Video.CONTENT_URI, values, SQLiteContract.Video._ID + " = ?", new String[]{String.valueOf(videoData.getId())});
//
//                        Intent progressIntent = new Intent(Constants.FINISH_DOWNLOAD_ACTION);
//                        _context.sendBroadcast(progressIntent);
//
//                        Toast.makeText(_context, "File removed!", Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//                viewHolder.downloadVideo.setVisibility(TextUtils.isEmpty(videoData.getFilePath()) ? View.VISIBLE : View.GONE);
//                viewHolder.downloadVideo.setImageResource(videoData.isPaid() ? R.drawable.download : R.drawable.download_paid);
//                viewHolder.playVideo.setVisibility(TextUtils.isEmpty(videoData.getFilePath()) ? View.GONE : View.VISIBLE);
//                viewHolder.removeVideo.setVisibility(TextUtils.isEmpty(videoData.getFilePath()) ? View.GONE : View.VISIBLE);
//                break;
//            default:
//                view = inflator.inflate(R.layout.lits_item_default, viewGroup, false);
//                break;
//        }
//
//
//        viewHolder.img = (ImageView) view.findViewById(R.id.item_image);
//        viewHolder.learnImage = (ImageView) view.findViewById(R.id.item_learn);
//        viewHolder.title = (TextView) view.findViewById(R.id.item_title);
//        viewHolder.title.setTypeface(_typeface);
//
//        viewHolder.itemId = cursor.getInt(0);
//        view.setTag(viewHolder);
//        return view;
//    }
//
//    @Override
//    public void bindView(View view, final Context context, Cursor cursor) {
//        if (view != null && cursor != null) {
//            ViewHolder viewHolder = (ViewHolder) view.getTag();
//            if (viewHolder.img != null) {
//                String imageName = cursor.getString(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_IMAGE_NAME));
//                if (null == imageName) {
//                    viewHolder.img.setImageDrawable(null);
//                    viewHolder.img.setVisibility(View.GONE);
//                } else {
//                    BitmapLoader bitmapLoader = new BitmapLoader(context);
//                    bitmapLoader.displayImage(imageName, viewHolder.img);
//                    viewHolder.img.setVisibility(View.VISIBLE);
//                }
//            }
//            if (viewHolder.learnImage != null) {
//                ItemType itemType = ItemType.parseFromCode(cursor.getInt(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_ITEM_TYPE)));
//                viewHolder.learnImage.setVisibility(itemType == ItemType.Lesson ? View.VISIBLE : View.GONE);
//            }
//            viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_NAME)));
//        }
//    }
//
//    @Override
//    public long getItemId(int position) {
//        Cursor cursor = getCursor();
//        if (cursor != null && cursor.moveToPosition(position)) {
//            return getCursor().getLong(getCursor().getColumnIndex(SQLiteContract.Texts._ID));
//        }
//        return -1;
//    }
//
//    @Override
//    public StripItem getItem(int position) {
//        Cursor cursor = getCursor();
//        if (cursor != null && cursor.moveToPosition(position)) {
//            StripItem stripItem = new StripItem(cursor.getInt(cursor.getColumnIndex(SQLiteContract.Texts._ID)));
//            stripItem.setImageName(cursor.getString(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_IMAGE_NAME)));
//            stripItem.setText(cursor.getString(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_NAME)));
//            stripItem.setRefID(cursor.getInt(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_REF_ID)));
//            stripItem.setItemType(ItemType.parseFromCode(cursor.getInt(cursor.getColumnIndex(SQLiteContract.Texts.COLUMN_ITEM_TYPE))));
//
//            return stripItem;
//        }
//        return null;
//    }
//
//    class ViewHolder {
//        ImageView img;
//        ImageView learnImage;
//        TextView title;
//        ImageButton downloadVideo;
//        ImageButton playVideo;
//        ImageButton removeVideo;
//        int itemId;
//    }


}
