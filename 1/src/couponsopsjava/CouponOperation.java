package couponsopsjava;

import java.util.*;


public class CouponOperation implements ICouponOperation {
    List<Website> webList;
    List<Coupon> couponsList;
    Map<Website, List<Coupon>> webCoupons;

    public CouponOperation() {
        this.webList = new ArrayList<>();
        this.couponsList = new ArrayList<>();


        this.webCoupons = new HashMap<>();
    }

    public void registerSite(Website w) {
        searchWebByDom(w);


        this.webList.add(w);
        this.webCoupons.put(w, new ArrayList<>());
    }

    private void searchWebByDom(Website w) {
        for (Website website : this.webList) {
            if (website.domain.equals(w.domain)) {
                throw new IllegalArgumentException();
            }
        }
    }


    public void addCoupon(Website w, Coupon c) {

        Website web = null;
        for (Website website : this.webList) {
            if (w.domain.equals(website.domain)) {
                web = website;
            }
        }
        if (web == null) {
            throw new IllegalArgumentException();
        }


        List<Coupon> coupons = this.webCoupons.get(web);

//        if (coupons == null) {
//            coupons = new ArrayList<>();
//        }
        if (coupons.contains(c)) {
            throw new IllegalArgumentException();
        }
        coupons.add(c);
        this.couponsList.add(c);
        webCoupons.putIfAbsent(web, coupons);

    }

    public Website removeWebsite(String domain) {
        Website web = null;
        for (Website website : webList) {
            if (website.domain.equals(domain)) {
                web = website;
            }
        }
        if (web == null) {
            throw new IllegalArgumentException();
        }

        webList.remove(web);
        List<Coupon> coupons = webCoupons.get(web);
        coupons = new ArrayList<>();
        webCoupons.remove(web);
        return web;
    }

    public Coupon removeCoupon(String code) {
        Coupon c = null;
        for (Coupon coupon : couponsList) {
            if (coupon.code.equals(code)) {
                c = coupon;
            }
        }
        if (c == null) {
            throw new IllegalArgumentException();
        }
        this.couponsList.remove(c);
        return c;
    }

    public boolean exist(Website w) {
        return this.webList.contains(w);
    }

    public boolean exist(Coupon c) {
        return this.couponsList.contains(c);
    }

    public Collection<Website> getSites() {
        return new ArrayList<>(this.webList);
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        return this.webCoupons.get(w);
    }

    public void useCoupon(Website w, Coupon c) {
        if (!webList.contains(w)) {
            throw new IllegalArgumentException();
        }
        List<Coupon> coupons = webCoupons.get(w);
        if (!coupons.contains(c)) {
            throw new IllegalArgumentException();
        }
        coupons.remove(c);

    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        List<Coupon> res = new ArrayList<>(couponsList);

        res.sort((a, b) -> {
            if (b.validity - a.validity != 0) {
                return Integer.compare(a.validity, b.validity);
            } else {
                int i = b.discountPercentage - a.discountPercentage;
                return i;
            }
        });

        return res;
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {

        List<Website> res = new ArrayList<>(webList);

        Collection<List<Coupon>> values = this.webCoupons.values();
        values.stream().sorted((a, b) -> b.size() - a.size());
return res;
    }
}
