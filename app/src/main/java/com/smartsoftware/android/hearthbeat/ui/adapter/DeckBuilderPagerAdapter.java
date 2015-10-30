package com.smartsoftware.android.hearthbeat.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartsoftware.android.hearthbeat.model.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;

import static rx.observables.MathObservable.sumLong;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 27.10.2015
 * Time: 17:14
 * Email: mrahbar.azad@gmail.com
 */
public class DeckBuilderPagerAdapter extends PagerAdapter {


    public interface PageFactory {
        View onPageCreated(Context context, Collection<Card> cards);
    }

    private PageFactory pageFactory;
    private int numPages;
    private List<List<Card>> pageCards;

    public DeckBuilderPagerAdapter(PageFactory pageFactory, int elementsPerPageCount, Collection<Collection<Card>> values) {
        this.pageFactory = pageFactory;

        sumLong(Observable.from(values)
                .map(list -> Math.round(Math.ceil(((double) list.size()) / elementsPerPageCount))))
                .subscribe(sum -> {
                    this.numPages = sum.intValue();
                });

        Observable.from(values)
                .flatMapIterable(elements -> {
                    List<List<Card>> pageCards1 = new ArrayList<>(numPages);

                    Observable.from(elements)
                            .window(elementsPerPageCount)
                            .subscribe(subObservable -> {
                                subObservable.toList().subscribe(c -> {
                                    pageCards1.add(c);
                                });
                            });

                    return pageCards1;
                })
                .toList()
                .subscribe(collections -> {
                    this.pageCards = collections;
                });
    }

    @Override
    public int getCount() {
        return numPages;
    }

    @Override
    public Object instantiateItem(ViewGroup pager, int position) {
        View view = null;

        if (pageFactory != null) {
            final List<Card> cards = pageCards.get(position);
            final Card card = cards.get(cards.size() - 1);
            card.getPlayerClass();

            view = pageFactory.onPageCreated(pager.getContext(), cards);
            pager.addView(view);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup pager, int position, Object view) {
        pager.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
