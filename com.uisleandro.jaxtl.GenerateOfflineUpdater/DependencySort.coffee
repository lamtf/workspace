class DependencySort
  ifItGotForeignKeysItWillBeBubleSorted=(z)->
    #console.log "bublesorting"
    find_index=(name)->
      i = 0
      while(i < z.length)
        if(name is z[i].className)
          return i
        i++
      return -1
    buble = true
    infiniteLoop = 0
    while(buble && infiniteLoop < 1000)
      buble = false
      infiniteLoop++;
      hi = 0
      while(hi < z.length)
        i = 0
        while(i < z[hi].fks.length)
          lo = find_index(z[hi].fks[i])
          if(-1 < lo and hi < lo)
            higher = z[lo]
            lower = z[hi]
            z[lo] = lower
            z[hi] = higher
            buble = true
          i++
        hi++
    #println infiniteLoop
    return z

  sort:(x)->
  #ifItGotNoForeignKeysItWillComeFirst:(x)->
    #console.log "quickSorting"
    i = 0
    y = []
    z = []
    while(i < x.length)
      if(x[i].fks.length is 0)
        y[y.length] = x[i]
      else
        z[z.length] = x[i]
      i++
    i = 0
    z = ifItGotForeignKeysItWillBeBubleSorted(z)
    while(i < z.length)
      y[y.length] = z[i]
      i++
    return y


test1=()->

  x = []
  x[0] = { className: "token", fks : ["user","system","token_type"]}
  x[1] = { className: "user", fks : ["country", "system", "role"]}
  x[2] = { className: "country", fks : []}
  x[3] = { className: "system", fks : ["currency","reseller"]}
  x[4] = { className: "role", fks : []}
  x[5] = { className: "currency", fks : []}
  x[6] = { className: "reseller", fks : []}
  s = new DependencySort()
  console.log(s.sort(x))


test1()

module.exports = DependencySort
