package btools.util;

/**
 * Memory efficient and lightning fast heap to get the lowest-key value of a set of key-object pairs
 *
 * @author ab
 */
public final class SortedHeap<V> {
  private int size;
  private int peaksize;
  private SortedBin first;
  private SortedBin second;
  private SortedBin firstNonEmpty;

  public SortedHeap() {
    clear();
  }

  /**
   * @return the lowest key value, or null if none
   */
  public V popLowestKeyValue() {
    SortedBin bin = firstNonEmpty;
    if (firstNonEmpty == null) {
      return null;
    }
    size--;
    SortedBin minBin = firstNonEmpty.getMinBin();
    return (V) minBin.dropLowest();
  }

  private static final class SortedBin {
    SortedHeap parent;
    SortedBin next;
    SortedBin nextNonEmpty;
    int binsize;
    int[] al; // key array
    Object[] vla; // value array
    int lv; // low value
    int lp; // low pointer

    SortedBin(int binsize, SortedHeap parent) {
      this.binsize = binsize;
      this.parent = parent;
      al = new int[binsize];
      vla = new Object[binsize];
      lp = binsize;
    }

    SortedBin next() {
      if (next == null) {
        next = new SortedBin(binsize << 1, parent);
      }
      return next;
    }

    Object dropLowest() {
      int lpOld = lp;
      if (++lp == binsize) {
        unlink();
      } else {
        lv = al[lp];
      }
      Object res = vla[lpOld];
      vla[lpOld] = null;
      return res;
    }

    void unlink() {
      SortedBin neBin = parent.firstNonEmpty;
      if (neBin == this) {
        parent.firstNonEmpty = nextNonEmpty;
        return;
      }
      for (; ; ) {
        SortedBin next = neBin.nextNonEmpty;
        if (next == this) {
          neBin.nextNonEmpty = nextNonEmpty;
          return;
        }
        neBin = next;
      }
    }

    void add(int key, Object value) {
      int p = lp;
      for (; ; ) {
        if (p == binsize || key < al[p]) {
          al[p - 1] = key;
          vla[p - 1] = value;
          lv = al[--lp];
          return;
        }
        al[p - 1] = al[p];
        vla[p - 1] = vla[p];
        p++;
      }
    }

    // unrolled version of above for binsize = 4
    void add4(int key, Object value) {
      int p = lp--;
      if (p == 4 || key < al[p]) {
        lv = al[p - 1] = key;
        vla[p - 1] = value;
        return;
      }
      lv = al[p - 1] = al[p];
      vla[p - 1] = vla[p];
      p++;

      if (p == 4 || key < al[p]) {
        al[p - 1] = key;
        vla[p - 1] = value;
        return;
      }
      al[p - 1] = al[p];
      vla[p - 1] = vla[p];
      p++;

      if (p == 4 || key < al[p]) {
        al[p - 1] = key;
        vla[p - 1] = value;
        return;
      }
      al[p - 1] = al[p];
      vla[p - 1] = vla[p];

      al[p] = key;
      vla[p] = value;
    }

    // unrolled loop for performance sake
    SortedBin getMinBin() {
      SortedBin minBin = this;
      SortedBin bin = this;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      if ((bin = bin.nextNonEmpty) == null) return minBin;
      if (bin.lv < minBin.lv) minBin = bin;
      return minBin;
    }
  }

  /**
   * add a key value pair to the heap
   *
   * @param key   the key to insert
   * @param value the value to insert object
   */
  public void add(int key, V value) {
    size++;

    if (first.lp == 0 && second.lp == 0) // both full ?
    {
      sortUp();
    }
    if (first.lp > 0) {
      first.add4(key, value);
      if (firstNonEmpty != first) {
        first.nextNonEmpty = firstNonEmpty;
        firstNonEmpty = first;
      }
    } else // second bin not full
    {
      second.add4(key, value);
      if (first.nextNonEmpty != second) {
        second.nextNonEmpty = first.nextNonEmpty;
        first.nextNonEmpty = second;
      }
    }

  }

  private void sortUp() {
    if (size > peaksize) {
      peaksize = size;
    }

    // determine the first array big enough to take them all
    int cnt = 8; // value count of first 2 bins is always 8
    SortedBin tbin = second; // target bin
    SortedBin lastNonEmpty = second;
    do {
      tbin = tbin.next();
      int nentries = tbin.binsize - tbin.lp;
      if (nentries > 0) {
        cnt += nentries;
        lastNonEmpty = tbin;
      }
    }
    while (cnt > tbin.binsize);

    int[] al_t = tbin.al;
    Object[] vla_t = tbin.vla;
    int tp = tbin.binsize - cnt; // target pointer

    // unlink any higher, non-empty arrays
    SortedBin otherNonEmpty = lastNonEmpty.nextNonEmpty;
    lastNonEmpty.nextNonEmpty = null;

    // now merge the content of these non-empty bins into the target bin
    while (firstNonEmpty != null) {
      // copy current minimum to target array
      SortedBin minBin = firstNonEmpty.getMinBin();
      al_t[tp] = minBin.lv;
      vla_t[tp++] = minBin.dropLowest();
    }

    tp = tbin.binsize - cnt;
    tbin.lp = tp; // new target low pointer
    tbin.lv = tbin.al[tp];
    tbin.nextNonEmpty = otherNonEmpty;
    firstNonEmpty = tbin;
  }

  public void clear() {
    size = 0;
    first = new SortedBin(4, this);
    second = new SortedBin(4, this);
    firstNonEmpty = null;
  }

  public int getSize() {
    return size;
  }

  public int getPeakSize() {
    return peaksize;
  }

  public int getExtract(Object[] targetArray) {
    int tsize = targetArray.length;
    int div = size / tsize + 1;
    int tp = 0;

    int lpi = 0;
    SortedBin bin = firstNonEmpty;
    while (bin != null) {
      lpi += bin.lp;
      Object[] vlai = bin.vla;
      int n = bin.binsize;
      while (lpi < n) {
        targetArray[tp++] = vlai[lpi];
        lpi += div;
      }
      lpi -= n;
      bin = bin.nextNonEmpty;
    }
    return tp;
  }
}
