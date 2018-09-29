class DependencySort

  orderByNumberOfFks:(coll, pivot)->
    if coll.length is 0
      return []
    a = 0
    lhs = []
    rhs = []
    while a < coll.length
      if coll[a].fks.length is pivot
        lhs[lhs.length] = coll[a]
      else
        rhs[rhs.length] = coll[a]
      a++
    pivot++
    rhs = @orderByNumberOfFks(rhs, pivot)
    if(lhs.length > 0)
      return @addToEnd(lhs, rhs)
    else
      return rhs

  # runs untill it finds the fists which has no fks
  # what is wrong with this function???
  orderByForeignKeys:(coll)->
    found = true
    while found
      found = false
      a = 0
      while a < coll.length
        b = a + 1
        while b < coll.length
          c = 0
          while c < coll[a].fks.length
            if coll[a].fks[c] is coll[b].className
              found = true
              tmp = coll[a]
              coll[a] = coll[b]
              coll[b] = tmp
            c++
          b++
        a++
    coll

  addToEnd:(_head,_tail)->
    #console.log "addToEnd"
    c = 0
    while(c < _tail.length)
      _head[_head.length] = _tail[c]
      c++
    return _head

  sort:(x)->
    @orderByForeignKeys(@orderByNumberOfFks x,0)

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
