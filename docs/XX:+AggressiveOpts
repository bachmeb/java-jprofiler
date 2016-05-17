# AggressiveOpts

## References
* http://stackoverflow.com/questions/2959878/what-flags-are-enabled-by-xxaggressiveopts-on-sun-jre-1-6u20

##### Check the difference between setting AggressiveOpts on and off
```
java -XX:-AggressiveOpts -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal -version > no_agg
java -XX:+AggressiveOpts -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal -version > agg
diff -U0 no_agg agg
```
```c
/*
--- no_agg	2016-05-17 12:33:31.182790779 -0400
+++ agg	2016-05-17 12:33:46.246790773 -0400
@@ -13 +13 @@
-     bool AggressiveOpts                           := false           {product}           
+     bool AggressiveOpts                           := true            {product}           
@@ -37 +37 @@
-     intx AutoBoxCacheMax                           = 128             {C2 product}        
+     intx AutoBoxCacheMax                           = 20000           {C2 product}        
@@ -46 +46 @@
-     intx BiasedLockingStartupDelay                 = 4000            {product}           
+     intx BiasedLockingStartupDelay                 = 500             {product}           
@@ -197 +197 @@
-     bool EliminateAutoBox                          = false           {C2 diagnostic}     
+     bool EliminateAutoBox                          = true            {C2 diagnostic}    
*/
```

