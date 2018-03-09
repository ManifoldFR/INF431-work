## Sequential Consistency

1. If `W(x,1)` is executed before `R(x,0)` then `x` contains value `1` when we check on T2 if it contains `0`: it throws a `RuntimeException`.
  
    But if T2 does `R(x,0)` before T1, which itself executes `W(x,1)` before T2 can do `R(x,1)`, the program terminates and no exceptions are thrown.
    
2. The program will always give a runtime exception. If T1 writes `1` to `x` before T2 checks `R(x,2)`, then
    * either T2 does `R(x,2)` immediately and we get a runtime exception
    * or T1 writes ``2`` to `x`, then T2 checks `R(x,2)`, which terminates with no exception. But then T2 checks `R(x,1)` which fails with a runtime exception.

    If not, then T2
 checks ``R(x,2)`` immediately and we get a runtime exception because `x` contains the value `0` at this time.

3. If thread 3 performs all of its tasks in succession without the other threads doing anything, we get: T3 writes `1` to `x`, then checks if `x` contains `1` (which is true), then checks if `y` contains `1`, which will cause a runtime exception because `y` is initialised at 0.

    ```
    T1:R(x,0)
    T2:R(y,0)
    T3:W(x,1)
    T3:R(x,1)
    T1:W(y,1)
    T3:R(y,1)
    T2:W(x,0)
    T2:R(x,0)    
    T4:W(y,0)
    T1:R(y,0)
    ```

    So, interleavings that execute with and without runtime exceptions both exist.
