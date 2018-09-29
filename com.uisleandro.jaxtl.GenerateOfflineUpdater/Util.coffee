println=(x)->
  console.log x


special_sort_for_updating_2=(z)->
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
  while(buble && infiniteLoop < 100)
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

special_sort_for_updating_1=(x)->
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
  z = special_sort_for_updating_2(z)
  while(i < z.length)
    y[y.length] = z[i]
    i++
  return y


String.prototype.toCamelCase =()->
  words = this.split('_')
  i = 0
  res = ""
  while i < words.length
    if i is 0
      res += words[i].substr(0,1).toLowerCase()+words[i].substr(1)
    else
      res += words[i].substr(0,1).toUpperCase()+words[i].substr(1)
    i++
  return res

String.prototype.ToCamelCase =()->
  words = this.split('_')
  i = 0
  res = ""
  while i < words.length
    res += words[i].substr(0,1).toUpperCase()+words[i].substr(1)
    i++
  return res


module.exports =
  "special_sort_for_updating_1":special_sort_for_updating_1
  "println":println
