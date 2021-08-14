# GC分析

JVM基本参数配置

```properties
#初始化堆大小 -Xms
-XX:InitialHeapSize=536870912
#最大堆大小 -Xmx
-XX:MaxHeapSize=536870912
#年轻带初始化大小 -Xmn设置后，表示-XX:NewSize和-XX:MaxNewSize相等
-XX:NewSize=402653184
#年轻代最大大小
-XX:MaxNewSize=402653184
#Survivor区占年轻带的比例 此处为1/8，因为存在两个Survivor
-XX:SurvivorRatio=8
#栈大小 -Xss，单位KB
-XX:ThreadStackSize=256
#元空间大小
-XX:MetaspaceSize=33554432
#最大元空间大小
-XX:MaxMetaspaceSize=188743680
```

## 串行收集器Serial

### JVM参数配置及说明

GC收集器使用`-XX:+UseSerialGC`，以启用SerialGC，开启该参数后年轻代和老年代都使用串行回收器，即年轻代使用Serial GC，老年代使用Serial Old GC。

### GCEasy分析图

![image-20210813191646077](/Users/yuxiao/Documents/学习笔记/JVMMemorySize-Serial.png)

![image-20210813191723348](/Users/yuxiao/Documents/学习笔记/GCPauseTime-Serial.png)

![image-20210813191845793](/Users/yuxiao/Documents/学习笔记/HeapBeforeGC-Serial.png)

![image-20210813192032472](/Users/yuxiao/Documents/学习笔记/GCStatistics-Serial.png)

### Serial小结

从上图的基本可以分析出，由于GC造成的STW时长总共是2.34s(GC日志总时长大约是15分钟左右，从启动开始)，期间共发生了3次Full GC，由于是串行，所以3次Full GC总耗时为810ms。

## 并行收集器ParNew&Parallel GC

### ParNew

#### JVM参数配置及说明

GC收集器使用-XX:+UseParNewGC，以启用ParNew并行收集器，ParNew是Serial的多线程版本，开启这个收集器后新生代使用并行回收器，老年代仍然使用串行回收器（Serial Old）。

#### GCEasy分析图

![image-20210813213118063](/Users/yuxiao/Documents/学习笔记/JVMMemorySize-parnew.png)

![image-20210813213159496](/Users/yuxiao/Documents/学习笔记/GCDurationTime分布图-parnew.png)

![image-20210813213245576](/Users/yuxiao/Documents/学习笔记/HeapBeforeGC-parnew.png)

![image-20210813213318081](/Users/yuxiao/Documents/学习笔记/GCStatistics-parnew.png)

#### ParNew小结

ParNew的GC总时间为1.78s，Serial的GC总时间为2.34s，从GC的总时间来看，并行垃圾收集器比串行的效率要高。主要体现在MonitorGC的效率上，ParNew的MonitorGC总时间为0.88s，而Serial的Monitor的总时间为1.53s，MonitorGC的时间减低了73.8%，由此来看，在年轻代使用并行收集器的效率比串行的效率要高出很多。

### Parallel GC

#### JVM参数配置及说明

GC收集器使用`-XX:+UseParallelOldGC`，以启用Parallel并行收集器。开启该参数后，新生代和老年代都使用并行收集器。
Parallel收集器提供了自适应的调节策略`-XX:+UseAdaptiveSizePolicy`，即JVM会根据实际运行情况动态调整新生代大小、Eden和Sruvivor区的比例、晋升老年代对象大小等相关参数。

#### GCEasy分析图

![image-20210813214808366](/Users/yuxiao/Documents/学习笔记/JVMMemorySize-parallel.png)

![image-20210813214841713](/Users/yuxiao/Documents/学习笔记/GCPauseTimeIndicators.png)

![image-20210813215125979](/Users/yuxiao/Documents/学习笔记/GCTimeStatistics-parallel.png)

#### Parallel小结

Parallel收集器GC总耗时1.67s，比ParNew的GC总时长1.78s略有所减低，其中Full GC的总耗时为740ms，比ParNew的Full
GC总耗时880ms减少了18.9%，此减少耗时得益于Parallel的老年代的并行收集器，由于年前都是并行收集器，几乎没有区别。

## CMS收集器

### JVM参数配置及说明

GC收集器使用`-XX:+UserConcMarkSweepGC`
，以启用CMS，是一个并发标记清除的收集器，即在GC期间可以和业务线程一起运行。CMS是老年代收集器，开启后，年轻代默认使用ParNew并行收集器。CMS收集器使用的标记清除算法，运行过程分为四个步骤，初始标记->并发标记->重新标记->
并发清除，由于在整个过程中存在并发的步骤（并发标记和并发清除），所以总体上CMS是一款并发收集器。

### GCEasy分析图

![image-20210813224513453](/Users/yuxiao/Documents/学习笔记/JVMMemorySize-CMS.png)

![image-20210813225332993](/Users/yuxiao/Documents/学习笔记/GCDurationTimeIndicators-CMS.png)

![image-20210813225535712](/Users/yuxiao/Documents/学习笔记/GCTimeStatistics-CMS.png)

### CMS小结

从上图中可以看出，CMS暂定业务线程的总时长为200ms(FinalRemakr) + 50ms(InitialMark) = 250ms，相比较Parallel Old收集器在老年代的耗时740ms降低了很多。

## G1

### JVM参数配置及说明

虚拟机参数配置-XX:+UseG1GC，以启用G1收集器。

### GCEasy分析图

![image-20210813231737947](/Users/yuxiao/Documents/学习笔记/JVMMemorySize-G1.png)

![image-20210813231808328](/Users/yuxiao/Documents/学习笔记/GCTimeIndicators-G1.png)

![image-20210813231907765](/Users/yuxiao/Documents/学习笔记/PauseGCDuration-G1.png)

![image-20210813231945075](/Users/yuxiao/Documents/学习笔记/GCTimeStatistics-G1.png)

![image-20210813232034772](/Users/yuxiao/Documents/学习笔记/GCCausesTime-G1.png)

### G1小结

从上图可以看出，G1收集器花费的总耗时为1.4+0.15=1.55s。而且G1收集器并未发送Full GC，总体而言G1收集器更为全面，不过G1收集器还有些JVM参数未调配，需要不断尝试调配参数，使其达到最优状态。

## 总结

从上面使用不同的垃圾收集器后的GC情况来开，

由于**串行收集器**使用的是单核心进行垃圾回收，造成的STW时间最长。

**并行收集器**时串行收集器的升级版本，在垃圾回收时使用了多核心进行并行手机，这样就可以缩短垃圾回收的时候，从而减少STW时间。

**CMS收集器**则再并行收集器的基础上再次进行了改良，作用于老年代，总体上来看使用了标记-清除算法，但由于清除后造成的空间碎片问题，CMS不得不在设置的几次Full
GC进行内存整理，从而保证内存的规整。CMS收集过程分为四个阶段：`初始标记，并发标记，重新标记，并发清除`
。由于并发标记和并发清除阶段是并发操作的，也就是说在这两个阶段垃圾回收动作和用户业务动作可以同时运行，所以这两个阶段不是STW的，所以总体上而言CMS减低地业务线程的停顿时间。

**G1收集器**是面向全堆的收集器，垃圾回收过程也分为四个阶段：`初始标记，并发标记，最终标记，筛选回收`
，从四阶段的名称来看，并发过程只有并发阶段，其他阶段仍然要暂停用户线程，即STW，和CMS相比较而言，其停顿时间会略微有所增加，但其目标是完成一个延迟可控的高吞吐量的全局收集器。