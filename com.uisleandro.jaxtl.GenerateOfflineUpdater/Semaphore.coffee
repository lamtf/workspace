class Semaphore
  constructor:()->
    @wc = 0
    @results = []
  then:(@then)->
  begin:($fun)->
    @wc++
    #console.log "Semaphore::begin(#{@wc})"
    if $fun
      $fun()
  # Eu so vou precisar do primeiro resultado, mas vou adicionar todos
  # aliás, posso adicionar null. Resolvido o problema
  end:(result)->
    @wc--
    #console.log "Semaphore::end(#{@wc})"
    if result
      @results.push result
    if @wc is 0
      @then(@results)

module.exports = Semaphore
