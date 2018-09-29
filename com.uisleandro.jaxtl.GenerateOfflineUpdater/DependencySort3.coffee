class DependencySort

  # Runs untill it finds the fists which has no fks
  # what is wrong with this function???
  sortByForeignKeys:(arr)->
    i = 0
    while i < arr.length
      j = i + 1
      while j < arr.length
        k = 0
        while k < arr[i].fks.length
          if arr[i].fks[k] is arr[j].name # && i < j
            tmp = arr[i]
            # console.log "SWAP ", arr[i].name, arr[j].name
            arr[i] = arr[j]
            arr[j] = tmp
          k++
        j++
      i++
    return arr

  # Testado e funcionando!!
  sort:(arr)->
    i = 0
    head = []
    tail = []
    while i < arr.length
      if arr[i].fks.length is 0
        head.push arr[i]
      else
        tail.push arr[i]
      i++
    @sortByForeignKeys tail
    i = 0
    while i < tail.length
      head.push tail[i]
      i++
    return head

module.exports = DependencySort
