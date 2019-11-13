<?xml version="1.0" encoding="UTF-8" standalone="no"?><map version="0.8.1"><node CREATED="1573655623685" ID="00oq0ufj7uk8duhpuvn762n1n9" MODIFIED="1573655623685" TEXT="简洁代码"><node CREATED="1573655623685" ID="4gkvq2ts52c24kp321m8osofkm" MODIFIED="1573655623685" POSITION="right" TEXT="命名"><node CREATED="1573655623685" ID="6ava6c8t2dciatebbokm98rdhv" MODIFIED="1573655623685" TEXT="名副其实，名字能够准确表达意图，避免使用a，b，c 变量"/><node CREATED="1573655623685" ID="0j05c673fk2944bjmkcvja334t" MODIFIED="1573655623685" TEXT="做有意义的区分"><node CREATED="1573655623685" ID="62ie29phqm4t93mevtnp12rdno" MODIFIED="1573655623685" TEXT="废话冗余命名: Product， ProductInfo， ProductData 都是同一类东西"/><node CREATED="1573655623685" ID="0f31ru6mv3j6vr083l8n8ff7db" MODIFIED="1573655623685" TEXT="数字系列命名: a1, a2, a3 ...."/></node><node CREATED="1573655623685" ID="0vfk7v9ae5v3i3o2ip1hn1ktc0" MODIFIED="1573655623685" TEXT="避免使用硬编码"><node CREATED="1573655623685" ID="0jnj69i40adjc5ag8ibsuo36v9" MODIFIED="1573655623685" TEXT="匈牙利语标记法: 名字带有类型编码(phoneString)"/><node CREATED="1573655623685" ID="66cbolrd1b9vdafpurd4hfv8mm" MODIFIED="1573655623685" TEXT="成员前缀: m_name"/><node CREATED="1573655623685" ID="71tu898rtkjianmbpp141u0flp" MODIFIED="1573655623685" TEXT="接口和实现: IFactory 和 FactoryImpl, 最好是实现类进行编码"/></node><node CREATED="1573655623685" ID="4vleksmdtefg96d6f1n7k696lk" MODIFIED="1573655623685" TEXT="类名"><node CREATED="1573655623685" ID="76c94qeeb357od81gpvehrebis" MODIFIED="1573655623685" TEXT="名词或者名词短语,    避免使用Manager，Processor这样的类名，无法精确表达类的职责"/></node><node CREATED="1573655623685" ID="66jpt672c6nro5ts5ongmc18q3" MODIFIED="1573655623685" TEXT="方法名"><node CREATED="1573655623685" ID="5lsvsblsm4en16vsn5folht5nq" MODIFIED="1573655623685" TEXT="动词或者动词短语"/></node><node CREATED="1573655623685" ID="349kaf63ri2275jrn6gsjp4olv" MODIFIED="1573655623685" TEXT="每个抽象对应一个词，一以贯之"><node CREATED="1573655623685" ID="2t9gpivpgo659suh1k3347kpfi" MODIFIED="1573655623685" TEXT="fetch，retrieve，get出现在多个类的同种方法的命名中"/></node><node CREATED="1573655623685" ID="7ei5l21vjhfuha72rp83dkk2lk" MODIFIED="1573655623685" TEXT="添加有意义的语境"><node CREATED="1573655623685" ID="27jpuum2p20ol1l6i8kpnij03p" MODIFIED="1573655623685" TEXT="需要有良好命名的类，函数或者名称空间来放置名称，为其提供具体的语境"/><node CREATED="1573655623685" ID="2pa3t6g5ed61kg8s9cm5k9995e" MODIFIED="1573655623685" TEXT="如果没有，给名称增加前缀，提供有意义的语境"/><node CREATED="1573655623685" ID="541ab4urs3a86ctfko20ks7pfd" MODIFIED="1573655623685" TEXT="例子: state 如果在Address类里面可以很好的识别；如果只是本地变量，最好增加前缀addr"/></node><node CREATED="1573655623685" ID="4lpemi874ptp74eca7iurmkagv" MODIFIED="1573655623685" TEXT="不要添加没有的语境"><node CREATED="1573655623685" ID="7pvqq76a2c0rkv18u024dh01e1" MODIFIED="1573655623685" TEXT="在GSD应用里面，为每个类增加GSD前缀 "/></node></node><node CREATED="1573655623685" ID="5danalmacvkfb1r4cet9l3apqu" MODIFIED="1573655623685" POSITION="right" TEXT="函数"><node CREATED="1573655623685" ID="5dv5ppgp6fr6fh70jtds8bv665" MODIFIED="1573655623685" TEXT="短小内聚"><node CREATED="1573655623685" ID="7jo963pfq0co3knkpgoqu8cgo7" MODIFIED="1573655623685" TEXT="代码行数要少"/><node CREATED="1573655623685" ID="1mrtd0c6hb74s5ko9vfa7ae163" MODIFIED="1573655623685" TEXT="缩进层级少"/></node><node CREATED="1573655623685" ID="23f88a2rmirmvfbs882dnfbpdd" MODIFIED="1573655623685" TEXT="只做一件事"><node CREATED="1573655623685" ID="1rcvm5hk4c5j913genalb1ak3n" MODIFIED="1573655623685" TEXT="函数只是做了该函数名下同一抽象层上的步骤，则函数还是只做了一件事&#13;&#10;(编写函数就是把函数名称对应的抽象拆分为另一抽象层上的一系列步骤)"/></node><node CREATED="1573655623685" ID="65t6klv97aa4u7gquus89lgqct" MODIFIED="1573655623685" TEXT="每个函数一个抽象层级"><node CREATED="1573655623685" ID="5ra4pa78ov90dckbnto0in19l1" MODIFIED="1573655623685" TEXT="自顶向下：每一个函数后面跟着位于下一个抽象层级的函数"/></node><node CREATED="1573655623685" ID="3ho12kg6eieakss1lmoh9svco2" MODIFIED="1573655623685" TEXT="使用描述性的名称(参考命名章节)"/><node CREATED="1573655623685" ID="4fq6s4du1cdfv4gpld64s9e2ls" MODIFIED="1573655623685" TEXT="函数参数"><node CREATED="1573655623685" ID="3lhq4nah00r0e47dg6nh3l5ns4" MODIFIED="1573655623685" TEXT="参数数量越少越好，零参函数最好"/></node><node CREATED="1573655623685" ID="2hnklgdp15g4sddlld1hl1s412" MODIFIED="1573655623685" TEXT="无副作用"><node CREATED="1573655623685" ID="0nrvumkqpaqk75qalvc4gm1gdp" MODIFIED="1573655623685" TEXT="函数的隐藏副作用会造成时序上的耦合(看书中例子)，容易出错"/><node CREATED="1573655623685" ID="10gdq8k75fmhdrv7asvfr11nvr" MODIFIED="1573655623685" TEXT="尽量避免输出参数的函数，重构成参数对象内部的方法"/></node><node CREATED="1573655623685" ID="4efpq9lu9m01m40pjmh9en5mf2" MODIFIED="1573655623685" TEXT="分隔指令与询问(读写分离)"/><node CREATED="1573655623685" ID="68n33aee6kjvbqt8tu5k7k5h7e" MODIFIED="1573655623685" TEXT="使用异常代替返回错误码"><node CREATED="1573655623685" ID="4c852ifg26qo4u7g3qgqcv8ukm" MODIFIED="1573655623685" TEXT="抽离try catch语句"/><node CREATED="1573655623685" ID="7onsr8tjujp5nptivrb1qa0f4p" MODIFIED="1573655623685" TEXT="错误码需要枚举，修改枚举会影响现有代码，异常类可以派生，不影响现有代码"/></node><node CREATED="1573655623685" ID="58no9610euuing0qbfdvs6c2re" MODIFIED="1573655623685" TEXT="结构化编程(参考简洁架构)"/></node><node CREATED="1573655623685" ID="019miku1n9soddvmbqs1th5661" MODIFIED="1573655623685" POSITION="right" TEXT="类"><node CREATED="1573655623685" ID="24gjd3evmubk4fmtnrten90rtq" MODIFIED="1573655623685" TEXT="类短小"><node CREATED="1573655623685" ID="7eomckoa9db2f0rl1ufv7b5vhm" MODIFIED="1573655623685" TEXT="类名需要概括出类的职责，如果类名太含混，无法表达类的职责，说明类太大"/><node CREATED="1573655623685" ID="18h11bjckaho2q9ls18dj927f0" MODIFIED="1573655623685" TEXT="单一职责"><node CREATED="1573655623685" ID="1dg7d0gnlvec640jgku6m6cgu6" MODIFIED="1573655623685" TEXT="类只封装一个职责，只有且仅有一条加以修改的理由"/><node CREATED="1573655623685" ID="7fiek9io2edle151v47k6fa2mu" MODIFIED="1573655623685" TEXT="系统应该由许多短小的类组成，而不是由少量巨大的类组成(不要担心数量巨大的短小类)"/></node><node CREATED="1573655623685" ID="2mocfm62g06qstkbn7jeq0tjqk" MODIFIED="1573655623685" TEXT="内聚"><node CREATED="1573655623685" ID="6bfdsaipje666kh6ulup41p7ib" MODIFIED="1573655623685" TEXT="类定义的字段被每个方法所使用，则类具有最大的内聚性(无法创建这样的类，但是要保持高的内聚性)"/><node CREATED="1573655623685" ID="0itne4ach7q7lom3oco4aai64n" MODIFIED="1573655623685" TEXT="保持内聚性就会得到许多短小的类(将一个大函数拆分成小函数时，必然会增加类的字段数量，将这些字段拆分到其他类才能保证类的内聚性)"/></node></node><node CREATED="1573655623685" ID="2i36lg5au4bq0qhjtg958rn9rt" MODIFIED="1573655623685" TEXT="为了修改而组织"><node CREATED="1573655623685" ID="27a5aqg0dilo13ifpg6q9hjbjn" MODIFIED="1573655623685" TEXT="开闭原则，支持扩展，关闭修改"/><node CREATED="1573655623685" ID="5okcdd99nv3aqne92s99qoopv4" MODIFIED="1573655623685" TEXT="依赖倒置原则， 隔离修改"/></node></node><node CREATED="1573655623685" ID="1pcorlop0t9vo4n2m1ujqubge9" MODIFIED="1573655623685" POSITION="left" TEXT="数据和模型"><node CREATED="1573655623685" ID="53j2c78sm2n7j3v0ucg5ccvnae" MODIFIED="1573655623685" TEXT="数据，对象的反对称性"><node CREATED="1573655623685" ID="055ln4jvnupbqbo79oknrgkp23" MODIFIED="1573655623685" TEXT="对象把数据隐藏于抽象之后 (面向对象代码)"/><node CREATED="1573655623685" ID="03mu58jh9vtf5pvrtdvc8j8jni" MODIFIED="1573655623685" TEXT="数据结构暴露其数据 (过程性代码)"/><node CREATED="1573655623685" ID="6h8tomkraid27948sqp4d67cbo" MODIFIED="1573655623685" TEXT="反对称性"><node CREATED="1573655623685" ID="03t1s09jo42bkef3n4pmee7v64" MODIFIED="1573655623685" TEXT="过程性代码便于在不改动既有数据结构的前提下添加新函数，面向对象代码便于在不改动既有函数的前提下添加新类"/><node CREATED="1573655623685" ID="18qis8anplgv2qs56aude1aj2q" MODIFIED="1573655623685" TEXT="过程性代码难以添加新数据结构，面向对象代码难以添加新函数"/></node></node><node CREATED="1573655623685" ID="2llnv8p6n2vti22imq614qok82" MODIFIED="1573655623685" TEXT="最小知道原则(demeter定律)"><node CREATED="1573655623685" ID="66msleb17mc4rkk9ngo3k39421" MODIFIED="1573655623685" TEXT="函数可以调用的方法(不和陌生人谈话)"><node CREATED="1573655623685" ID="6rdpk3th4e3sk4qq2ljq06onnu" MODIFIED="1573655623685" TEXT="函数对应的类的方法"/><node CREATED="1573655623685" ID="0grd5p8v60dtdgnoffqm7j168s" MODIFIED="1573655623685" TEXT="函数内部创建的对象的方法"/><node CREATED="1573655623685" ID="1lrjml5sgckcl7tkuv7bbundkv" MODIFIED="1573655623685" TEXT="参数对象的方法"/><node CREATED="1573655623685" ID="20ju6mm2aibm78re3764t0s6ic" MODIFIED="1573655623685" TEXT="函数对应的类所持有的对象的方法"/></node><node CREATED="1573655623685" ID="777ldi032k5m7pf31ps1kn58mc" MODIFIED="1573655623685" TEXT="ctx.getOptions().getDir().getAbsolutePath() 是否违反demeter定律取决于ctx， options，dir 是对象还是数据结构"/><node CREATED="1573655623685" ID="1rvgppu1icgb8r9nnk65el7sor" MODIFIED="1573655623685" TEXT="隐藏结构: 把 absolutePath的交互逻辑封装成ctx的方法"/></node><node CREATED="1573655623685" ID="7s72faupl5mq5nq3rbvbhei6ru" MODIFIED="1573655623685" TEXT="数据的抽象(隐藏数据的实现)"><node CREATED="1573655623685" ID="6cm7krs28etuboi8phjrqjhopr" MODIFIED="1573655623685" TEXT="getGallonsOfGasoline (具象)-&gt; getPercentFuelRemaining (抽象，无法了解数据实体)"/><node CREATED="1573655623685" ID="18ij5v4o957ri1qrcvbtclr696" MODIFIED="1573655623685" TEXT="类通过暴露抽象接口，而不是通过取值器和赋值器将其数据暴露，用户无需了解数据的实体"/></node><node CREATED="1573655623685" ID="6i4d5u1qu8f9gqidhjkgci2qop" MODIFIED="1573655623685" TEXT="数据传输对象 DTO (只有字段和getter，setter，没有其他函数)"><node CREATED="1573655623685" ID="7l9atvcmgu1orf0mp59ijdbrr3" MODIFIED="1573655623685" TEXT="使用在 数据库传输 套接字传输"/></node></node></node></map>