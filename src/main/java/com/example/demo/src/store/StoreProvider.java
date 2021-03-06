package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
import com.example.demo.src.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class StoreProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;
    private final UserProvider userProvider;

    @Autowired
    public StoreProvider(StoreDao storeDao, UserProvider userProvider) {
        this.storeDao = storeDao;
        this.userProvider = userProvider;
    }

    // ************************************************************************************

    // 최신순 정렬
    @Transactional(readOnly = true)
    public List<GetStoreRes> getCategoryByDate(int userIdx, int categoryIdx, String deliveryService, int page, int pageSize) throws BaseException {

        if (userProvider.checkUser(userIdx) == 0 ) {
            throw new BaseException(EMPTY_USER);
        }

        if (storeDao.checkCategory(categoryIdx) == 0) {
            throw new BaseException(EMPTY_CATEGORY);
        }


        try {
            List<GetStoreRes> getCategoryRes;

            // category => 전체보기
            if (storeDao.checkCategoryIdx(categoryIdx) == 1) {
                // 전체보기 - 최신순 정렬 + 배달 가능 설정 안 함
                if (deliveryService.equals("N")) {
                    getCategoryRes = storeDao.getAllByDate(userIdx, page, pageSize);
                }
                // 전체보기 - 최신순 정렬 + 배달 가능
                else {
                    getCategoryRes = storeDao.getAllByDateAndDelivery(userIdx, deliveryService, page, pageSize);
                }
            }
            // category => 한식 ..
            else {
                // 카테고리 - 최신순 정렬 + 배달 가능 설정 안 함
                if (deliveryService.equals("N")) {
                    getCategoryRes = storeDao.getCategoryByDate(userIdx, categoryIdx, page, pageSize);
                }
                // 카테고리 - 최신순 정렬 + 배달 가능
                else {
                    getCategoryRes = storeDao.getCategoryByDateAndDelivery(userIdx, categoryIdx, deliveryService, page, pageSize);
                }
            }
            return getCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 별점순 정렬
    @Transactional(readOnly = true)
    public List<GetStoreRes> getCategoryByStarRate(int userIdx, int categoryIdx, String deliveryService, int page, int pageSize) throws BaseException {

        if (userProvider.checkUser(userIdx) == 0) {
            throw new BaseException(EMPTY_USER);
        }

        if (storeDao.checkCategory(categoryIdx) == 0) {
            throw new BaseException(EMPTY_CATEGORY);
        }

        try {
            List<GetStoreRes> getCategoryRes;

            // category => 전체보기
            if (storeDao.checkCategoryIdx(categoryIdx) == 1) {
                // 전체보기 - 별점순 정렬 + 배달 가능 설정 안 함
                if (deliveryService.equals("N")) {
                    getCategoryRes = storeDao.getAllByStarRate(userIdx, page, pageSize);
                }
                // 전체보기 - 별점순 정렬 + 배달 가능
                else {
                    getCategoryRes = storeDao.getAllByStarRateAndDelivery(userIdx, deliveryService, page, pageSize);
                }
            }
            // category => 한식 ..
            else {
                // 카테고리 - 최신순 정렬 + 배달 가능 설정 안 함
                if (deliveryService.equals("N")) {
                    getCategoryRes = storeDao.getCategoryByStarRate(userIdx, categoryIdx, page, pageSize);
                }
                // 카테고리 - 최신순 정렬 + 배달 가능
                else {
                    getCategoryRes = storeDao.getCategoryByStarRateAndDelivery(userIdx, categoryIdx, deliveryService, page, pageSize);
                }
            }
            return getCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    //전체 식당 조회
    @Transactional(readOnly = true)
    public List<GetStoresRes> getStores(int userIdx) throws BaseException {
        try{
            List<GetStoresRes> getStoresRes = storeDao.getStores(userIdx);
            return getStoresRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //삭제 전, 식당 존재 확인
    @Transactional(readOnly = true)
    public int deleteCheckStore(int userIdx, int storeIdx) throws BaseException{
        try{
            return storeDao.deleteCheckStore(userIdx, storeIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //식당 생성 전, 식당 존재 확인
    @Transactional(readOnly = true)
    public int checkStore(int userIdx, String storeName, String address){

        return storeDao.checkStore(userIdx, storeName, address);
    }

}

