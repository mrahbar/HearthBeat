package com.smartsoftware.android.hearthbeat.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.ActivityView;
import com.smartsoftware.android.hearthbeat.ui.adapter.CollectionPagerAdapter;
import com.smartsoftware.android.hearthbeat.ui.widget.CardViewAware;
import com.smartsoftware.android.hearthbeat.ui.widget.DashboardLayout;
import com.smartsoftware.android.hearthbeat.utils.ImageLoaderUtils;
import com.smartsoftware.android.hearthbeat.utils.Utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.BindInt;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class CollectionView implements ActivityView, CollectionPagerAdapter.PageFactory,
        View.OnClickListener, SearchView.OnQueryTextListener {

    @BindInt(R.integer.card_grid_columns)
    int cardGridColumns;

    @BindInt(R.integer.card_grid_rows)
    int cardGridRows;

    @Bind(R.id.collection_viewpager)
    ViewPager viewPager;

    @BindDimen(R.dimen.toolbar_height_material)
    int toolbarHeight;

    @Bind(R.id.collection_toolbar)
    Toolbar toolbar;

    private DeckBuilderViewListener listener;
    private int cardViewWidth, cardViewHeight;
    private Spinner classNameSpinner;
    private Map<String, List<Card>> originalCards, filteredCards;
    private boolean toolbarAnimated = false;
    private ViewPagerSpinnerStateHandler viewPagerSpinnerStateHandler;

    public interface DeckBuilderViewListener {
        void bindViews(CollectionView view);
        void showCard(CardIntentBundle cardBundle);
        void onFinish();
        Resources getResources();
    }

    public CollectionView(DeckBuilderViewListener listener, Map<String, List<Card>> cards) {
        this.listener = listener;
        this.originalCards = cards;
    }

    public void bind(BaseActivity activity) {
        filteredCards = new HashMap<>();

        activity.setContentView(getLayout());
        initializeView();
        initializeClassNameSpinner(originalCards);
        initializeToolbar();
        initializeViewPager(originalCards);
        initializeCardViewSize(activity);
    }

    public int getLayout() {
        return R.layout.activity_collection;
    }

    private void initializeView() {
        listener.bindViews(this);
        classNameSpinner = new Spinner(toolbar.getContext());
        viewPagerSpinnerStateHandler = new ViewPagerSpinnerStateHandler(classNameSpinner, viewPager);
    }

    private void initializeClassNameSpinner(Map<String, List<Card>> cards) {
        final String[] orderedClassNames = listener.getResources().getStringArray(R.array.class_names);

        Observable.from(orderedClassNames)
                .filter(s -> cards.keySet().contains(s))
                .toList()
                .subscribe(e -> {
                    ArrayAdapter<CharSequence> classNamesAdapter = new ArrayAdapter(toolbar.getContext(),
                            R.layout.spinner_element, e.toArray(new String[e.size()]));
                    classNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classNameSpinner.setAdapter(classNamesAdapter);
                });
    }

    private void initializeToolbar() {
        ArrayAdapter<CharSequence> cardSetAdapter = ArrayAdapter.createFromResource(toolbar.getContext(),
                R.array.card_set, R.layout.spinner_element);
        cardSetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner cardSetSpinner = new Spinner(toolbar.getContext());
        cardSetSpinner.setAdapter(cardSetAdapter);
        cardSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    initializeViewPager(originalCards);
                } else {
                    final String cardSet = listener.getResources().getStringArray(R.array.card_set)[position];
                    filterCards(entry -> {
                        List<Card> filtered = new ArrayList<>();
                        for (Card c : entry.getValue()) {
                            if (TextUtils.equals(c.getCardSet(), cardSet)) {
                                filtered.add(c);
                            }
                        }
                        return new AbstractMap.SimpleEntry<>(entry.getKey(), filtered);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        toolbar.addView(classNameSpinner);
        toolbar.addView(cardSetSpinner);
        toolbar.inflateMenu(R.menu.collection);
        setupSearchView(toolbar.getMenu());

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> listener.onFinish());
    }

    private void initializeViewPager(Map<String, List<Card>> cards) {
        Collection<Collection<Card>> cardsList = new ArrayList<>();
        final String[] classNames = listener.getResources().getStringArray(R.array.class_names);
        for (String className : classNames) {
            List<Card> collection = new ArrayList<>(cards.get(className));
            Collections.sort(collection, (lhs, rhs) -> Utils.compare(lhs.getCost(), rhs.getCost()));
            cardsList.add(collection);
        }

        int elementsPerPageCount = cardGridColumns * cardGridRows;
        CollectionPagerAdapter adapter = new CollectionPagerAdapter(this, elementsPerPageCount, cardsList);

        viewPagerSpinnerStateHandler.updateCards(adapter.getCount(), cards.size(), elementsPerPageCount, cardsList);
        viewPager.setAdapter(adapter);
        classNameSpinner.setSelection(0);
    }

    private void initializeCardViewSize(BaseActivity activity) {
        Point displaySize = Utils.getDisplaySize(activity);
        cardViewWidth = displaySize.x/cardGridColumns;
        cardViewHeight = Math.round(1.5f * cardViewWidth);
    }

    public void onResume() {
        if (toolbarAnimated) {
            toolbar.animate()
                    .translationYBy(toolbarHeight)
                    .withEndAction(() -> toolbarAnimated = false);
        }
    }

    @Override
    public View onPageCreated(Context context, Collection<Card> cards) {
        DashboardLayout gridLayout = new DashboardLayout(context);
        gridLayout.setGrid(cardGridColumns, cardGridRows);
        gridLayout.setLayoutParams(new ViewPager.LayoutParams());

        for (Card c : cards) {
            ImageView cardView = new ImageView(context);
            cardView.setTag(c);
            cardView.setOnClickListener(this);
            cardView.setMinimumWidth(cardViewWidth);
            cardView.setMinimumHeight(cardViewHeight);

            CardViewAware viewAware = new CardViewAware(cardView, cardViewWidth, cardViewHeight);
            ImageLoader.getInstance().displayImage(c.getImg(), viewAware, ImageLoaderUtils.CARD_IMAGE_NORMAL);

            gridLayout.addView(cardView);
        }

        return gridLayout;
    }

    private void setupSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_collection_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        searchView.setSubmitButtonEnabled(false);
        searchView.setSearchableInfo(null);

        final Context context = searchView.getContext();
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTextColor(ContextCompat.getColor(context, R.color.default_white_color));
        searchAutoComplete.setHintTextColor(ContextCompat.getColor(context, R.color.default_white_color));

        searchView.setQueryHint(context.getString(R.string.menu_collection_search_hint));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(() -> {
            initializeViewPager(originalCards);
            return false;
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterCardsByQuery(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterCardsByQuery(newText);
        return true;
    }

    private void filterCardsByQuery(String query) {
        if (query.length() > 2) {
            filterCards(entry -> {
                List<Card> filtered = new ArrayList<>();
                for (Card c : entry.getValue()) {
                    if (c.getName().toLowerCase().contains(query.toLowerCase())) {
                        filtered.add(c);
                    }
                }
                return new AbstractMap.SimpleEntry<>(entry.getKey(), filtered);
            });
        }
    }

    private void filterCards(FilterStrategy filterStrategy) {
        Observable.just(originalCards)
                .map(Map::entrySet)
                .flatMapIterable(entries -> entries)
                .map(filterStrategy::filterCardEntry)
                .collect((Func0<HashMap<String, List<Card>>>) HashMap::new, (collector, entry) -> collector.put(entry.getKey(), entry.getValue()))
                .subscribe(filteredMap -> {
                    filteredCards = filteredMap;
                    initializeViewPager(filteredCards);
                });
    }

    private interface FilterStrategy {
        AbstractMap.SimpleEntry<String, List<Card>> filterCardEntry(Map.Entry<String, List<Card>> entry);
    }

    @Override
    public void onClick(View v) {
        Card card = (Card) v.getTag();

        CardIntentBundle bundle = new CardIntentBundle();
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);

        int topOffset = v.getPaddingTop()+Utils.convertDipsToPixels(listener.getResources(), 6);

        bundle.cardId = card.getCardId();
        bundle.thumbnailHeight = cardViewHeight;
        bundle.thumbnailWidth = v.getWidth();
        bundle.thumbnailLeft = screenLocation[0];
        bundle.thumbnailTop = screenLocation[1] - topOffset;

        toolbar.animate()
                .translationYBy(-toolbarHeight)
                .withEndAction(() ->{
                    toolbarAnimated = true;
                    listener.showCard(bundle);
                });

    }

    public static class CardIntentBundle {
        private static final String TARGET_LEFT_TAG = "targetLeftTag";
        private static final String TARGET_TOP_TAG = "targetTopTag";
        private static final String TARGET_WIDTH_TAG = "targetWidthTag";
        private static final String TARGET_HEIGHT_TAG = "targetHeightTag";
        private static final String TARGET_CARDID_TAG = "targetCardIdTag";

        public String cardId;
        public int thumbnailTop, thumbnailLeft;
        public int thumbnailWidth, thumbnailHeight;

        public Bundle toBundle() {
            final Bundle bundle = new Bundle();
            bundle.putString(TARGET_CARDID_TAG, cardId);
            bundle.putInt(TARGET_HEIGHT_TAG, thumbnailHeight);
            bundle.putInt(TARGET_WIDTH_TAG, thumbnailWidth);
            bundle.putInt(TARGET_LEFT_TAG, thumbnailLeft);
            bundle.putInt(TARGET_TOP_TAG, thumbnailTop);

            return bundle;
        }

        public static CardIntentBundle fromBundle(Bundle bundle) {
            CardIntentBundle intentBundle = new CardIntentBundle();

            intentBundle.cardId = bundle.getString(TARGET_CARDID_TAG);
            intentBundle.thumbnailHeight = bundle.getInt(TARGET_HEIGHT_TAG);
            intentBundle.thumbnailWidth = bundle.getInt(TARGET_WIDTH_TAG);
            intentBundle.thumbnailLeft = bundle.getInt(TARGET_LEFT_TAG);
            intentBundle.thumbnailTop = bundle.getInt(TARGET_TOP_TAG);

            return intentBundle;
        }
    }

    public static class ViewPagerSpinnerStateHandler
            extends ViewPager.SimpleOnPageChangeListener
            implements AdapterView.OnItemSelectedListener {

        private Spinner spinner;
        private ViewPager viewPager;
        private boolean ignoreEvent = false;
        private ArrayList<Integer> pagePositionClassMap, classPagePositionMap;


        public ViewPagerSpinnerStateHandler(Spinner spinner, ViewPager viewPager) {
            this.spinner = spinner;
            this.viewPager = viewPager;
            registerListener();
        }

        private void registerListener() {
            spinner.setOnItemSelectedListener(this);
            viewPager.addOnPageChangeListener(this);
        }

        private void unregisterListener() {
            spinner.setOnItemSelectedListener(null);
            viewPager.removeOnPageChangeListener(this);
        }

        public void updateCards(int pageCount, int classCount, int elementsPerPageCount,
                                Collection<Collection<Card>> cardsList) {

            pagePositionClassMap = new ArrayList<>(pageCount);
            classPagePositionMap = new ArrayList<>(classCount);

            Observable.from(cardsList)
                    .map(list -> Math.round(Math.ceil(((double) list.size()) / elementsPerPageCount)))
                    .subscribe(new Action1<Long>() {
                        int classNameIndex = 0;
                        int sum = 0;

                        @Override
                        public void call(Long pageCount) {
                            int count = pageCount.intValue();
                            classPagePositionMap.add(sum);
                            sum += count;

                            for (int i = 0; i < count; i++) {
                                pagePositionClassMap.add(classNameIndex);
                            }
                            classNameIndex++;
                        }
                    });
        }

        @Override
        public void onPageSelected(int position) {
            int lastSelectedSpinnerIndex = spinner.getSelectedItemPosition();
            int index = pagePositionClassMap.get(position);

            if (lastSelectedSpinnerIndex != index) {
                ignoreEvent = true;
                unregisterListener();
                spinner.setSelection(index);
                registerListener();
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (ignoreEvent) {
                ignoreEvent = false;
                return;
            }

            int page = classPagePositionMap.get(position);

            unregisterListener();
            viewPager.setCurrentItem(page, true);
            registerListener();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}

