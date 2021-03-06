package com.zhangwan.app.cache;

import com.imnjh.imagepicker.util.SystemUtil;
import com.zhangwan.app.MyApplication;

import java.io.File;

/**
 * Created by Martin on 2017/1/17.
 */
public class CacheManager {

  public static final String ROOT_STORE = "PickerSample";

  private InnerCache imageInnerCache;

  private LocalCache imageCache;

  private static CacheManager instance;

  private CacheManager() {
    init();
  }

  public static synchronized CacheManager getInstance() {
    if (instance == null) {
      instance = new CacheManager();
    }
    return instance;
  }


  private void init() {
    initRootCache(SystemUtil.getStoreDir(MyApplication.getInstance()));
  }

  private boolean initRootCache(File storage) {
    File    cacheRoot = new File(storage, ROOT_STORE);
    boolean isMkRoot  = true;
    if (!cacheRoot.exists()) {
      isMkRoot = cacheRoot.mkdirs();
    } else if (!cacheRoot.isDirectory()) {
      isMkRoot = false;
    }
    if (!isMkRoot) {
      return false;
    }
    return true;
  }

  public InnerCache getImageInnerCache() {
    if (imageInnerCache == null) {
      imageInnerCache = new InnerCache();

    }
    return imageInnerCache;
  }

  public LocalCache getImageCache() {
    if (imageCache == null) {
      imageCache = new LocalCache("Image");
    }
    return imageCache;
  }
}
