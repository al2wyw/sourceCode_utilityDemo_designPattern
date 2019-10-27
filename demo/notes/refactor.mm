<?xml version="1.0" encoding="UTF-8" standalone="no"?><map version="0.8.1"><node CREATED="1572178431356" ID="676p7jqgm7d7c3r23gmv3n50ft" MODIFIED="1572178431356" TEXT="重构"><node CREATED="1572178431356" ID="675jjlrhd5o2ckmh6dg00gmoh2" MODIFIED="1572178431356" POSITION="right" TEXT="第一个案例"><node CREATED="1572178431356" ID="0gp4cg9hrr4v1uv852ncecsufd" MODIFIED="1572178431356" TEXT="一个函数只使用了别的类的数据，并没有使用当前类的数据，可以移动(Move Method)"/><node CREATED="1572178431356" ID="25e4us4vh8tpam2cbqcf2f31gc" MODIFIED="1572178431356" TEXT="Movie生命周期可以改price code，于是把code对应的price计算逻辑抽象出来，使用多态进行实行(replace conditional with polymorphism)，更改code时只要更改price的实现类(replace with state/strategy)"/></node><node CREATED="1572178431356" ID="1u8cugdonas37ci8574ebmjg0f" MODIFIED="1572178431356" POSITION="right" TEXT="重构原则"><node CREATED="1572178431356" ID="0avjtbamqm6kgu26nknvs4o5fa" MODIFIED="1572178431356" TEXT="两顶帽子"><node CREATED="1572178431356" ID="3s459pi3lqo9sjjgb4ftoll3kg" MODIFIED="1572178431356" TEXT="添加新功能，不能修改现有代码"/><node CREATED="1572178431356" ID="6l4gj890t8d0qlvo8mf6uq55m8" MODIFIED="1572178431356" TEXT="重构，不添加功能，尽量不修改任何测试代码"/></node><node CREATED="1572178431356" ID="39lr2847kip5v9ft0jhfmh66gh" MODIFIED="1572178431356" TEXT="为何重构"><node CREATED="1572178431356" ID="0q1o831en6n5dodgbgtkjjbk1a" MODIFIED="1572178431356" TEXT="改进软件设计"><node CREATED="1572178431356" ID="7d7sff0pbrvugl9puqeckr7ee8" MODIFIED="1572178431356" TEXT="防止程序设计的腐败:  为了短期目的或是不理解整体设计就贸然修改代码, 程序逐渐失去结构"/><node CREATED="1572178431356" ID="1gj101op3o79pjprj3f0me73lk" MODIFIED="1572178431356" TEXT="消除重复代码"/></node><node CREATED="1572178431356" ID="1nse87cvssm6rjp2853r1e50t6" MODIFIED="1572178431356" TEXT="使软件更容易理解"/><node CREATED="1572178431356" ID="6d15ei2acgh187evkjrg0c079j" MODIFIED="1572178431356" TEXT="提高编程效率"/></node><node CREATED="1572178431356" ID="4dpqjg6ohtj3hjt37bojjf309m" MODIFIED="1572178431356" TEXT="何时重构"><node CREATED="1572178431356" ID="3f5fsrsb1g5pn4othu2h0vnrjq" MODIFIED="1572178431356" TEXT="三次法则(事不过三，三则重构)"/><node CREATED="1572178431356" ID="5k5i27o7pmh78bjg11hbmj9p3c" MODIFIED="1572178431356" TEXT="添加新功能时重构"><node CREATED="1572178431356" ID="77bu1fr37a20v592rncjf9f58s" MODIFIED="1572178431356" TEXT="理清结构，清晰化逻辑，使代码更快更容易被理解"/><node CREATED="1572178431356" ID="7cvnh7o260u06sejc39putlpff" MODIFIED="1572178431356" TEXT="快速轻松添加功能"/></node><node CREATED="1572178431356" ID="7c4debn0eqbr6c3lresi1hkaj4" MODIFIED="1572178431356" TEXT="修补错误时重构"/></node><node CREATED="1572178431356" ID="1lbko4kns39nis5lu1s3f0ohnu" MODIFIED="1572178431356" TEXT="重构的价值"><node CREATED="1572178431356" ID="3vot7akddn2gcskelsb97ojatu" MODIFIED="1572178431356" TEXT="程序的两面价值，今天能做什么，明天能做什么；只关注今天的功能，导致明天无法完成新的功能"/><node CREATED="1572178431356" ID="1hlmtvrd9dmk936o1jckvd4reu" MODIFIED="1572178431356" TEXT="容易阅读； 易于扩展；条件逻辑简单；新功能不修改已有代码"/></node><node CREATED="1572178431356" ID="1r5qo10pb3g3u8gtp0mruerv0c" MODIFIED="1572178431356" TEXT="重构的难题"/><node CREATED="1572178431356" ID="1h7qf7g1c5tk4k94tva5mmgm7f" MODIFIED="1572178431356" TEXT="重构与设计"/><node CREATED="1572178431356" ID="2h07pfk2upvb5sn2pm65sqva5b" MODIFIED="1572178431356" TEXT="重构与性能"/></node><node CREATED="1572178431356" FOLDER="true" ID="0r0m3heivknm9lkjl1g4kas5aj" MODIFIED="1572178431356" POSITION="right" TEXT="代码的坏味道"><node CREATED="1572178431356" ID="6brmt2cfjpkqtn4sqfe0de9hdm" MODIFIED="1572178431356" TEXT="Duplicated Code"/><node CREATED="1572178431356" ID="3k7hp9cls89g4u73s32lujluat" MODIFIED="1572178431356" TEXT="Long Method"/><node CREATED="1572178431356" ID="5mpuald55avtem2he87dpgks0e" MODIFIED="1572178431356" TEXT="Large Class"/><node CREATED="1572178431356" ID="25h22p6sirckm23q6ncu75c64l" MODIFIED="1572178431356" TEXT="Long Parameter List"/><node CREATED="1572178431356" ID="7fgl6q6fvdjk4sq3vrkm4ntnm7" MODIFIED="1572178431356" TEXT="Divergent Change 发散式变化"><node CREATED="1572178431356" ID="31rr6i92gh9bcvvbid5pupnlab" MODIFIED="1572178431356" TEXT="一个类受多种变化的影响"><node CREATED="1572178431356" ID="7o16ah2kij2r6jkgl55889niik" MODIFIED="1572178431356" TEXT="数据库更改，需要更改某三个函数"/><node CREATED="1572178431356" ID="18kk3n65gakfe46cklo351u8cf" MODIFIED="1572178431356" TEXT="新增类型，需要更改某四个函数"/></node></node><node CREATED="1572178431356" ID="55n4kkhn2falnu9v271mipt659" MODIFIED="1572178431356" TEXT="shotgun surgery 霰弹式修改"><node CREATED="1572178431356" ID="2n7884iqrrulqt9k4btu8uveud" MODIFIED="1572178431356" TEXT="一个变化引起多个类修改"/></node><node CREATED="1572178431356" ID="610jqp2kdd8ev7hamsv337fjq8" MODIFIED="1572178431356" TEXT="Parallel inheritance Hierarchies 平行继承体系"><node CREATED="1572178431356" ID="1k9rh0qv076tn0cqri6vphfhv6" MODIFIED="1572178431356" TEXT="霰弹式的特例，一个类增加子类，另一个类也同样要增加子类"/></node><node CREATED="1572178431356" ID="6ql680ii2djr2t12npg908iedj" MODIFIED="1572178431356" TEXT="Feature Envy 依恋情节"><node CREATED="1572178431356" ID="2didqp52dbmdpmuhr7nf3nd5tr" MODIFIED="1572178431356" TEXT="某个函数过度依赖其他类的字段数据"/></node><node CREATED="1572178431356" ID="1bvqo5lalrr9oc2m1uce38rdvd" MODIFIED="1572178431356" TEXT="inappropriate Intimacy 过分亲密"><node CREATED="1572178431356" ID="7p7lfa51vnbu7igbfg22pqqivk" MODIFIED="1572178431356" TEXT="互相依赖对方的private数据"/></node><node CREATED="1572178431356" ID="4p49o73b2mahh1m00qs6m82ds2" MODIFIED="1572178431356" TEXT="Data Clumps 数据泥团"><node CREATED="1572178431356" ID="5fvjjcr69rvplu68krqdeksadg" MODIFIED="1572178431356" TEXT="几个字段出现在多个不同的类，可以把它们提炼到同一个类中"/></node><node CREATED="1572178431356" ID="328en24repmk5o25er9k1hvgtq" MODIFIED="1572178431356" TEXT="Switch Statements"/><node CREATED="1572178431356" ID="2ju7jc6eumu1vlouusavcsm8s5" MODIFIED="1572178431356" TEXT="Lazy Class 冗余类(用不到的类)"/><node CREATED="1572178431356" ID="2robi96atf4ue0r01aefoc6inj" MODIFIED="1572178431356" TEXT="speculative generality 夸夸其谈未来性"><node CREATED="1572178431356" ID="4l7fa759b6b2adc5q7bbd22dlp" MODIFIED="1572178431356" TEXT="过度设计，多余的参数，多余的方法"/></node><node CREATED="1572178431356" ID="6696sg7dkn2am491qv5kv8f3jg" MODIFIED="1572178431356" TEXT="Temporary Field 冗余字段"><node CREATED="1572178431356" ID="3sf6k93l79i886646ulqtlrvut" MODIFIED="1572178431356" TEXT="只有在某种场景下，此字段才被使用"/></node><node CREATED="1572178431356" ID="7pghdm7gkkju7gbojpor3b0iru" MODIFIED="1572178431356" TEXT="Message Chain 过度耦合的消息链"><node CREATED="1572178431356" ID="4916cr95j40ihts2jl16s688ac" MODIFIED="1572178431356" TEXT="与目标类的数据构造耦合在一起，getTargetA().getTargetB().getTargetC() -&gt; getTargetC()"/></node><node CREATED="1572178431356" ID="7vr605c2nps703fslimm2ai8kj" MODIFIED="1572178431356" TEXT="Alternative Classes with Different Interfaces 异曲同工的类"><node CREATED="1572178431356" ID="0l9o7iapjoetuvsk9dfmrjbhtj" MODIFIED="1572178431356" TEXT="不同名字但功能一样的类"/></node><node CREATED="1572178431356" ID="1kgg7dj6hkcoqaqt74id6246iq" MODIFIED="1572178431356" TEXT="Incomplete Library Class 不完善的类库"><node CREATED="1572178431356" ID="7uc9o210g4rr26o6qu1gnu1ect" MODIFIED="1572178431356" TEXT="改善类库，适用到我们的场景"/></node><node CREATED="1572178431356" ID="1i9c3lpi0c3v5hk45vt5krip8u" MODIFIED="1572178431356" TEXT="Data class 纯粹的数据类"><node CREATED="1572178431356" ID="5644hfls8f8l18miv53516ja5i" MODIFIED="1572178431356" TEXT="只有数据字段，没有方法函数的类"/></node><node CREATED="1572178431356" ID="578ajkabsf7d5pi52r3u8i14ol" MODIFIED="1572178431356" TEXT="Middle Man 中间人 关于委托和继承 ???"/><node CREATED="1572178431356" ID="54paju6jlvi4aqq1fbp77mg4dp" MODIFIED="1572178431356" TEXT="Refused Bequest 拒绝的馈赠(继承带来的)"><node CREATED="1572178431356" ID="21hfln33gpibf7bcsjre3g64iu" MODIFIED="1572178431356" TEXT="使用委托来替代继承"/></node><node CREATED="1572178431356" ID="6ltsgm8dmi77hku1psubf1rsda" MODIFIED="1572178431356" TEXT="Comments 过多的注释"><node CREATED="1572178431356" ID="5kf4akse54v6ai9tdt0mr38712" MODIFIED="1572178431356" TEXT="当你要注释时，先尝试提炼出方法从而避免过多注释"/></node></node><node CREATED="1572178431356" ID="1od8cf0akevu7aiecbjfbudidn" MODIFIED="1572178431356" POSITION="right" TEXT="处理继承关系"><node CREATED="1572178431356" ID="4rduac77bd9sn452konk5rv43t" MODIFIED="1572178431356" TEXT="Pull Up Field 字段上移"><node CREATED="1572178431356" ID="2n22q9s7qo192m6upqetne1muj" MODIFIED="1572178431356" TEXT="字段是公有的，而非某个子类特有，此时可以上移"/></node><node CREATED="1572178431356" ID="42dth868cs1rnigf2m2t0j8ahu" MODIFIED="1572178431356" TEXT="Pull Up Method 函数上移"><node CREATED="1572178431356" ID="6pdvu0jaglba0rj6itr1grh8no" MODIFIED="1572178431356" TEXT="通过调整参数和改名，使得子类的函数保持一致，然后上移函数"/><node CREATED="1572178431356" ID="13oarckkhai6ppkttajj2874ne" MODIFIED="1572178431356" TEXT="如果两个函数相似而不相同，可以使用Form Template Method方法，然后上移函数"/><node CREATED="1572178431356" ID="5lh88fnaurfr12hu883r4q0q5a" MODIFIED="1572178431356" TEXT="如果两个函数本体不同但是作用相同，可以用一个函数来替换另一个函数，然后上移函数"/><node CREATED="1572178431356" ID="6t1v2jk5295e0ufalpf1inoaui" MODIFIED="1572178431356" TEXT="函数引用了子类的字段或者函数，需要一起上移或者在父类建立抽象方法"/></node><node CREATED="1572178431356" ID="3dj1t87mtgdl691svnas7d6rc2" MODIFIED="1572178431356" TEXT="Pull Up Constructor Body 构造函数本体上移"/><node CREATED="1572178431356" ID="7t0on9ibsmr7hf9bgn8n2mobdu" MODIFIED="1572178431356" TEXT="Push Down Method 函数下移"><node CREATED="1572178431356" ID="6bvgpjoso648jv62q2medbed8f" MODIFIED="1572178431356" TEXT="父类中某个函数只与某个子类相关(非全部子类)"/></node><node CREATED="1572178431356" ID="7pnm4nqj2li27fvgmkh5tf1qoe" MODIFIED="1572178431356" TEXT="Push Down Field 字段下移"><node CREATED="1572178431356" ID="3use9pv0qpim6l5gcn4q3pqocs" MODIFIED="1572178431356" TEXT="父类中某个字段只与某个子类相关(非全部子类)"/></node><node CREATED="1572178431356" ID="2th77egbuh7t34a92ehn9ggjf8" MODIFIED="1572178431356" TEXT="Extract SubClass 提炼子类"><node CREATED="1572178431356" ID="6cn7bd9kdavm05lja97v2h9t5k" MODIFIED="1572178431356" TEXT="类中的某些函数只被一些实例使用，或者函数中的某些行为由类型代码来区分"/><node CREATED="1572178431356" ID="4v2l4nrhj0maikqfd3dtu0qlto" MODIFIED="1572178431356" TEXT="与Extract Class的区别是一个是委托，一个是继承"/><node CREATED="1572178431356" ID="4i3v29qeupn99u0f42kihlaokk" MODIFIED="1572178431356" TEXT="如果对象创建完成，无法改变与类型相关的行为(继承的锅)"/></node><node CREATED="1572178431356" ID="4adedgg6eg2bd9cs216179glf6" MODIFIED="1572178431356" TEXT="Extract SuperClass 提炼父类"><node CREATED="1572178431356" ID="6ts2d9lgictqice6jtp9n7krpn" MODIFIED="1572178431356" TEXT="两个类以相同或者不同的方式做着类似的事情，修改时需要多处同时修改"/><node CREATED="1572178431356" ID="0kpaecl7hn6jvnbrmasj4liisk" MODIFIED="1572178431356" TEXT="与Extract Class的区别是一个是委托，一个是继承"/><node CREATED="1572178431356" ID="14e3mh7qkbm9p30j8v9lo1fcma" MODIFIED="1572178431356" TEXT="参考pull up method的总结"/></node><node CREATED="1572178431356" ID="09lngn1as417qs6d4qj47nefkl" MODIFIED="1572178431356" TEXT="Extract Interface 提炼接口"><node CREATED="1572178431356" ID="7hio2efah442uimdus9pqp0dfi" MODIFIED="1572178431356" TEXT="使用一个类的某些功能，而非全部功能，或者使用某些类的某些相同功能，将这些功能全部抽离出来形成接口"/><node CREATED="1572178431356" ID="4h4jr9hp7paav23np8o2mvm6qa" MODIFIED="1572178431356" TEXT="本重构可能产生重复代码，可以使用Extract Class 抽离公共功能，再把共同功能进行委托处理"/><node CREATED="1572178431356" ID="5fvt9dsr6hhuq9ge0bpk43t7er" MODIFIED="1572178431356" TEXT="某个类在不同场景下有不同的职责，提炼出接口来描述这些职责"/><node CREATED="1572178431356" ID="6rj09es3l0elff9jtbss0jbbgu" MODIFIED="1572178431356" TEXT="使用接口来描述某个类的外部依赖"/></node><node CREATED="1572178431356" ID="5fj084as0j1agsb0m9c6ho7srl" MODIFIED="1572178431356" TEXT="Form Template Method 构造模板方法"><node CREATED="1572178431356" ID="3tcq1lpatoo0i8drgapfo32ase" MODIFIED="1572178431356" TEXT="将方法中相同的操作抽离出来，差异的操作变成抽象方法"/></node><node CREATED="1572178431356" ID="0qioqjeogpumjeqt2kaehmrioi" MODIFIED="1572178431356" TEXT="Collapse Hierarchy 折叠继承体系"><node CREATED="1572178431356" ID="4gb2hfpgtql59goijm513vagci" MODIFIED="1572178431356" TEXT="子类和父类相差无几，可以合二为一"/></node><node CREATED="1572178431356" ID="4on5rlaf7tgo5bp1hg2og2q49v" MODIFIED="1572178431356" TEXT="Replace Inheritance with Delegation 以委托取代继承"><node CREATED="1572178431356" ID="3djth5e3shc376bbg0de56qape" MODIFIED="1572178431356" TEXT="只使用了父类接口的一部分方法，或者不需要继承来的字段"/></node><node CREATED="1572178431356" ID="3jkcoenm24e189hv1qu5gj0te7" MODIFIED="1572178431356" TEXT="Replace Delegation with Inheritance 以继承取代委托"><node CREATED="1572178431356" ID="7918fv9gcatalihq3u21ienfo0" MODIFIED="1572178431356" TEXT="需要使用委托类的所有函数，需要编写大量简单的委托方法"/><node CREATED="1572178431356" ID="67atpja6gdrv9e182fr92dq2bh" MODIFIED="1572178431356" TEXT="如果并没有使用委托类的所有函数，就不应该使用本重构方法，而是使用remove middle man,让用户直接使用委托对象，或者使用Extract SuperClass将两个接口相同部分提炼出来"/><node CREATED="1572178431356" ID="1n74tl927l3hr8ncfanq3d03eg" MODIFIED="1572178431356" TEXT="当委托对象可变时，无法使用本重构方法"/></node><node CREATED="1572178431356" ID="0stc6us9e19j0mmuspu4jkppd2" MODIFIED="1572178431356" TEXT="继承总结"><node CREATED="1572178431356" ID="2q3ngnpdk8078l558seqahqj85" MODIFIED="1572178431356" TEXT="pull 和 push 进行子类和父类的提炼(接口)"/><node CREATED="1572178431356" ID="03nukr14c3a5i5mili1if391a2" MODIFIED="1572178431356" TEXT="构造模板方法"/><node CREATED="1572178431356" ID="2cpvpi3a1hb71vfk71krtg0mu7" MODIFIED="1572178431356" TEXT="继承和委托互换"/></node><node CREATED="1572178431356" ID="4oge08petn7ukaq0jlrjjjnnca" MODIFIED="1572178431356" TEXT="Factory Method可以隐藏子类的构造"/></node><node CREATED="1572178431356" ID="40ppg1t9aresnpc9d74359ok3b" MODIFIED="1572178431356" POSITION="right" TEXT="简化函数调用"><node CREATED="1572178431356" ID="028dhcc1p2rl4n7bjqt7lnmno1" MODIFIED="1572178431356" TEXT="Rename Method 函数改名"/><node CREATED="1572178431356" ID="0d86gdoa8ccf44lrb322c1aj5t" MODIFIED="1572178431356" TEXT="Add Parameter 增加参数"/><node CREATED="1572178431356" ID="6uvvhkmq7dhslnbdjmdb85td0a" MODIFIED="1572178431356" TEXT="Remove Parameter 移除参数"/><node CREATED="1572178431356" ID="58nbg1t5ro5qm76622n2sr1auj" MODIFIED="1572178431356" TEXT="Parameterize Method 参数化函"><node CREATED="1572178431356" ID="6a75u4h5ikh6v9okvnuvhdr2nm" MODIFIED="1572178431356" TEXT="参数的值不会改变函数行为"/><node CREATED="1572178431356" ID="7epb058crqpvd951p2rm90cdo9" MODIFIED="1572178431356" TEXT="函数体内变量不写死，以参数来替代"/></node><node CREATED="1572178431356" ID="3jv1gt78hql8fkg6pj2obmni6r" MODIFIED="1572178431356" TEXT="Replace Parameter with Explicit Methods 以函数代替参数"><node CREATED="1572178431356" ID="515kos7mqml9gfvij6lt8lmbtd" MODIFIED="1572178431356" TEXT="参数的值决定了函数的行为"/><node CREATED="1572178431356" ID="4m6mq48rpc9ak8hrii3fjg249j" MODIFIED="1572178431356" TEXT="函数体内避免对参数值的有效性做校验"/><node CREATED="1572178431356" ID="1o5c7dttjed5r60v74urdpilim" MODIFIED="1572178431356" TEXT="函数体内避免出现if-else逻辑判断"/><node CREATED="1572178431356" ID="6vubejgf2qjh578d6fq77d3b3c" MODIFIED="1572178431356" TEXT="接口更加清晰，Switch.beOn() 比 Switch.setState(true) 更加明白"/></node><node CREATED="1572178431356" ID="5d1vouvia671bqe3p7bprkae57" MODIFIED="1572178431356" TEXT="Replace Parameter with Methods 以函数代替参数"><node CREATED="1572178431356" ID="4br9sgl5ft3rj75h0hileg5ovg" MODIFIED="1572178431356" TEXT="int i = ob.getCount();&#13;&#10;ob.calculate(i, this);"/><node CREATED="1572178431356" ID="687mvkhnesfhth9gf4hio55rht" MODIFIED="1572178431356" TEXT="ob.calculate(this);"/><node CREATED="1572178431356" ID="4tavc92g33kr3h4ast6nch8gk7" MODIFIED="1572178431356" TEXT="移除 getCount 调用"/></node><node CREATED="1572178431356" ID="2nuh95rsu9089pcrv5aa0pt3sd" MODIFIED="1572178431356" TEXT="Preserve Whole Object 保持对象完整"><node CREATED="1572178431356" ID="2agqgj251uuacjku56b2l6okdd" MODIFIED="1572178431356" TEXT="函数调用时传递整个对象，而不是对象中的某些字段"/><node CREATED="1572178431356" ID="1jujvqnp3t4fgbd2d9rgplj8a1" MODIFIED="1572178431356" TEXT="方便以后进行扩展，不需要修改函数签名"/><node CREATED="1572178431356" ID="0dp52li47lj9rpl58qe54bif2j" MODIFIED="1572178431356" TEXT="可以使用对象所提供的函数"/><node CREATED="1572178431356" ID="1agghsmbg4roonf1jlt35dj3ae" MODIFIED="1572178431356" TEXT="提高代码的可读性，一大串的参数难以理解"/><node CREATED="1572178431356" ID="0ju7qtangco9739ckfn9etvqpm" MODIFIED="1572178431356" TEXT="调用者需要依赖参数对象，可能会导致依赖恶化"/></node><node CREATED="1572178431356" ID="3kble9si56ggdo6u78c9130rri" MODIFIED="1572178431356" TEXT="Introduce Parameter Object 引入参数对象"><node CREATED="1572178431356" ID="71oembgvqsg3701gla7fp9ombo" MODIFIED="1572178431356" TEXT="缩短参数列表，避免数据泥团"/><node CREATED="1572178431356" ID="57nmablqsfsqtgajvnbg5ros2p" MODIFIED="1572178431356" TEXT="可以移动函数到参数对象中，避免代码重复"/></node><node CREATED="1572178431356" ID="2raorp2j6lfdh5nsect59gvqd0" MODIFIED="1572178431356" TEXT="Replace Constructor with Factory Method 工厂函数取代构造函数"><node CREATED="1572178431356" ID="4171egbf9hgrtdm6uegi8oa673" MODIFIED="1572178431356" TEXT="隐藏派生类， 在派生子类时以工厂函数取代类型码"><node CREATED="1572178431356" ID="4fiotd2jbd6o1bb78btvirhc89" MODIFIED="1572178431356" TEXT="为所有子类提供唯一一个工厂函数"/><node CREATED="1572178431356" ID="07e19dq9at449abui86d6pbrap" MODIFIED="1572178431356" TEXT="为每个子类提供明确的工厂函数(子类不在增删)"/></node></node><node CREATED="1572178431356" ID="4d1ln89gv6b67i07rr0so7bjiu" MODIFIED="1572178431356" TEXT="Separate Query From Modifier 查询和修改分离"><node CREATED="1572178431356" ID="486a3mumbg2bv2quae0s6de1pn" MODIFIED="1572178431356" TEXT="查询可以使用缓存单独优化"/><node CREATED="1572178431356" ID="5dtksavjjo842q01lma77r26ec" MODIFIED="1572178431356" TEXT="查询避免副作用，职责单一"/></node><node CREATED="1572178431356" ID="7d3ero0fqvraefap9c13clmkco" MODIFIED="1572178431356" TEXT="Remove Setting Method 移除设置函数"><node CREATED="1572178431356" ID="51blsnjnijo6lo9sod2lf1qnhp" MODIFIED="1572178431356" TEXT="不变对象保证其不变性"/></node><node CREATED="1572178431356" ID="4b1og6dnvbgipcb1lkgg93s82c" MODIFIED="1572178431356" TEXT="Hide Method 隐藏函数"><node CREATED="1572178431356" ID="392obdmr5477juobj4slg1pcn1" MODIFIED="1572178431356" TEXT="不需要暴露的函数可以降低函数的可见度，改为private"/></node><node CREATED="1572178431356" ID="630gjjvi8kfnmor554tu51oued" MODIFIED="1572178431356" TEXT="Encapsulate Downcast 封装向下转型"><node CREATED="1572178431356" ID="4m1m6hdd204n7pi93tj29iu0pt" MODIFIED="1572178431356" TEXT="函数直接返回对应的类型，而不是Object类型，避免调用者进行转型"/></node><node CREATED="1572178431356" ID="4tociaungk6ju7g5a7qp2f7cvu" MODIFIED="1572178431356" TEXT="Replace Error with Exception 异常替代错误码"/><node CREATED="1572178431356" ID="2ag8nfo8p2iuqcnq3nmaapu8q9" MODIFIED="1572178431356" TEXT="Replace Exception with Test 以条件校验替代异常"><node CREATED="1572178431356" ID="3rteaov5qjus2gjlt3vq10r38h" MODIFIED="1572178431356" TEXT="对于非异常场景，意料之内的输入，使用条件检查而非异常"/></node><node CREATED="1572178431356" ID="37738h10j4nk9m5fitliv80i2j" MODIFIED="1572178431356" TEXT="函数调用总结"><node CREATED="1572178431356" ID="7suiaai7099equiodtkrr8cveo" MODIFIED="1572178431356" TEXT="参数增加与减少"/><node CREATED="1572178431356" ID="5kmsva6n89t65g72p9ggsriipt" MODIFIED="1572178431356" TEXT="参数对象"/><node CREATED="1572178431356" ID="0umchbup2jmhr4jsunu1cl1531" MODIFIED="1572178431356" TEXT="函数调用规范"/></node><node CREATED="1572178431356" ID="6ct55hm70mgpgvad5r314uqk13" MODIFIED="1572178431356" TEXT="注意重构过程都要考虑修改对原来的影响，是否保留原来的函数"/></node><node CREATED="1572178431356" ID="4955g6g9mga8abu94oat02b7eu" MODIFIED="1572178431356" POSITION="right" TEXT="简化条件表达式"><node CREATED="1572178431356" ID="6dtcqdf05en10tjlsjph5beuku" MODIFIED="1572178431356" TEXT="Decompose Conditional 分解条件表达式"><node CREATED="1572178431356" ID="4l937tua46qh0fqvjlfnre0gev" MODIFIED="1572178431356" TEXT="从if-else段落中分别提炼出独立函数"/></node><node CREATED="1572178431356" ID="6d4o3bsp4nd8ri0l875au4shbi" MODIFIED="1572178431356" TEXT="Consolidate Conditional 合并条件表达式"><node CREATED="1572178431356" ID="0s0lpaqgl25vj2hiaelfjumias" MODIFIED="1572178431356" TEXT="条件表达式的结果都一样，合并成为一个并提炼成独立函数"/></node><node CREATED="1572178431356" FOLDER="true" ID="786i6evbu8djhbc21mjd28khe7" MODIFIED="1572178431356" TEXT="Consolidate Duplicate Conditional Fragment 合并条件分支中重复的部分"><node CREATED="1572178431356" ID="48dui43hk5n5k179jel4jous04" MODIFIED="1572178431356" TEXT="每个分支最后都调用了同一个函数，可以把这个函数移动到if-else外面"/></node><node CREATED="1572178431356" ID="3sm9vgk6s567494blnq65a35p0" MODIFIED="1572178431356" TEXT="Replace Nested Conditional with Guard Clauses 卫语句代替嵌套"/><node CREATED="1572178431356" ID="20ka11t7k9a337m3i5bu10prqf" MODIFIED="1572178431356" TEXT="Replace Conditional with Polymorphism 以多态取代条件表达式 !!!"/><node CREATED="1572178431356" FOLDER="true" ID="0cjh911mibf4r394c293q60o2q" MODIFIED="1572178431356" TEXT="Introduce Null Object 引入null对象"><node CREATED="1572178431356" ID="6he18u981docmhgkt3e46lp6ha" MODIFIED="1572178431356" TEXT="如果需要在多个地方检查对象是否为NULL，不如直接用NULL对象来避免检查"/></node><node CREATED="1572178431356" FOLDER="true" ID="04ruanmtrg9rbpuglggrq9qqem" MODIFIED="1572178431356" TEXT="Remove Control Flag 移除控制标记"><node CREATED="1572178431356" ID="5u7jqf2id27vuqef9jnbkg1j61" MODIFIED="1572178431356" TEXT="使用 break, continue 替代控制变量"/><node CREATED="1572178431356" ID="4875jl78s2fr49tiu2gvmq0mgh" MODIFIED="1572178431356" TEXT=" 使用extract method 提炼方法，在提炼的方法中使用 return 结束"/></node><node CREATED="1572178431356" ID="0rbdehgmg3ntvb4k9q6aus4rcf" MODIFIED="1572178431356" TEXT="Introduce Assertion 引入断言(避免出现 if 判断)"><node CREATED="1572178431356" ID="456krngs3rr344nie6divb2ije" MODIFIED="1572178431356" TEXT="断言抛出非检查异常"/><node CREATED="1572178431356" ID="3f4homtr0nbjic7lto8ra6jt1q" MODIFIED="1572178431356" TEXT="禁止在不影响程序的正常运行的地方使用断言"/></node><node CREATED="1572178431356" ID="7fu70abvce678ck5eo24gpv4bt" MODIFIED="1572178431356" TEXT="条件总结"><node CREATED="1572178431356" ID="40o4burpkm45cajcab8ji6sm1h" MODIFIED="1572178431356" TEXT="条件语句简化"/><node CREATED="1572178431356" ID="53l88e7vdsjmsh1l2a181eqnee" MODIFIED="1572178431356" TEXT="多态代替条件语句"/></node><node CREATED="1572178431356" ID="0a1378ea5d9srvpo5p612vf2rl" MODIFIED="1572178431356" TEXT="多态代替条件语句"/><node CREATED="1572178431356" ID="0rsqevkd4bl3aa81cft1bqpd87" MODIFIED="1572178431356" TEXT="条件语句简化"/></node><node CREATED="1572178431356" ID="6qr6j6skievpjkkde7bhtimi1n" MODIFIED="1572178431356" POSITION="left" TEXT="重新组织数据"><node CREATED="1572178431356" ID="3it42l3tfe1q0tgf08bv83jcto" MODIFIED="1572178431356" TEXT="Self Encapsulate Field 自封装字段"><node CREATED="1572178431356" ID="5cr3foti14u93ju4d6id1p9upt" MODIFIED="1572178431356" TEXT="使用设值/取值方法来访问字段"/></node><node CREATED="1572178431356" ID="1k5dgja4mf7bcn344e51h541kd" MODIFIED="1572178431356" TEXT="Replace Data Value with Object 以对象代替数据值"><node CREATED="1572178431356" ID="55vas2amcnjpvqcscsi82bvi2s" MODIFIED="1572178431356" TEXT="String customer -&gt; class Customer"/></node><node CREATED="1572178431356" ID="5k2ogbga6di3h37itr1totdvmb" MODIFIED="1572178431356" TEXT="Change Value to Reference 将值对象改为引用对象"/><node CREATED="1572178431356" ID="7dl3b122knn45ouup4hp2uc8nh" MODIFIED="1572178431356" TEXT="Change Reference to Value 将引用对象改为值对象"/><node CREATED="1572178431356" ID="100sappuoik4dd9r6hnvpgoega" MODIFIED="1572178431356" TEXT="Replace Array/Map with Object 以对象取代数组/映射"><node CREATED="1572178431356" ID="434jr7s4ptom4vri128kovgd0o" MODIFIED="1572178431356" TEXT="让数据表达得更清晰明确"/></node><node CREATED="1572178431356" ID="24rnkpoo0d82943c6ue70tjn9g" MODIFIED="1572178431356" TEXT="Duplicate Observed Data 复制&quot;被监视数据&quot; !!!"><node CREATED="1572178431356" ID="16p626j9qmip7je8bu21r21ik4" MODIFIED="1572178431356" TEXT="使用Observer模式同步领域对象和表现层内的重复数据"/></node><node CREATED="1572178431356" ID="0somj0cibjf4s2fudiqk9q3qmu" MODIFIED="1572178431356" TEXT="将单向关联改为双向关联/将双向关联改成单向关联"><node CREATED="1572178431356" ID="1eee1vcajnsdm0tgbaf5289r4g" MODIFIED="1572178431356" TEXT="创建一对多关联关系，然后由某个类来控制关联(在新增移除元素时)"/></node><node CREATED="1572178431356" ID="3gjq1j9vl8tk07k7uhig6pc8dd" MODIFIED="1572178431356" TEXT="Replace Magic Number with Symbolic Constant 常量代替魔数"/><node CREATED="1572178431356" ID="0sco83mj3lrrb9sn1f5hu1hldu" MODIFIED="1572178431356" TEXT="Encapsulate Field 封装字段"><node CREATED="1572178431356" ID="7c5e8393tnutbe0un85j8seqrp" MODIFIED="1572178431356" TEXT="把public的字段改成private并提供setter和getter方法"/></node><node CREATED="1572178431356" ID="5depp26bm7fdj1mi8m3qppghme" MODIFIED="1572178431356" TEXT="Encapsulate Collection 封装集合"><node CREATED="1572178431356" ID="4q4m38jgn2ae3qlafnq1thv194" MODIFIED="1572178431356" TEXT="读: 不返回集合本身，隐藏数据集合的实现"/><node CREATED="1572178431356" ID="2n9kl9ctbtn941g7ngo2gsrupr" MODIFIED="1572178431356" TEXT="写: 不能直接操作集合，而是提供操作方法, 集合的拥有者可以控制元素的添加修改"/><node CREATED="1572178431356" ID="0g3clgv6bmgolb0kd0b97skt6r" MODIFIED="1572178431356" TEXT="涉及集合元素的访问的方法，使用Move Method 移动到集合的拥有者里面"/></node><node CREATED="1572178431356" ID="55j70eljkocpscv7oi4mbaqvo1" MODIFIED="1572178431356" TEXT="Replace Type Code with Class 以类取代类型码"><node CREATED="1572178431356" ID="0fobhh2lq9uinnal5sgr5tuvde" MODIFIED="1572178431356" TEXT="类型码不影响函数的行为(特别的: 没有出现在switch语句里)"/><node CREATED="1572178431356" ID="0lfpsnmf78oaqnhc23010291gn" MODIFIED="1572178431356" TEXT="可以使用 Move Method 把相关方法也一起移植到新的 Class"/></node><node CREATED="1572178431356" ID="6lvrjq6o66ml0vhlairtgj5ukc" MODIFIED="1572178431356" TEXT="Replace Type Code with Subclass 以子类取代类型码"/><node CREATED="1572178431356" ID="711oo9edt7hnpls6bls1rioksh" MODIFIED="1572178431356" TEXT="Replace Type Code with State/Strategy 以State/Strategy取代类型码"><node CREATED="1572178431356" ID="51hr7vt8a2icese0oqe9qmlp61" MODIFIED="1572178431356" TEXT="类型码的值在对象生命周期会变化"/><node CREATED="1572178431356" ID="0dmt7aofjcjcoov922bj210r1l" MODIFIED="1572178431356" TEXT="宿主类不能被继承"/></node><node CREATED="1572178431356" ID="5s2nrde892b30grq9d29fu6gfj" MODIFIED="1572178431356" TEXT="Replace Subclass with Field 以字段取代子类"><node CREATED="1572178431356" ID="20fv2r68f4rgif911eso2fv4q4" MODIFIED="1572178431356" TEXT="如果各个子类的差别只是在于返回常量数据的几个函数，那么在超类采用新增字段(final)来代替各个子类， 超类要提供Factory method来构造各种不同字段值的对象"/></node><node CREATED="1572178431356" ID="7ovignrrmtj9hsf99hq9mmpvv3" MODIFIED="1572178431356" TEXT="数据总结"><node CREATED="1572178431356" ID="7mgv5sh1op7n5agc1afgmgmlbd" MODIFIED="1572178431356" TEXT="封装字段，集合"/><node CREATED="1572178431356" ID="5k1fuebvt3h84hqubktgap8k0s" MODIFIED="1572178431356" TEXT="Type Code的处理, 简单子类的处理"/><node CREATED="1572178431356" ID="453189no37d3irargmemeho1d3" MODIFIED="1572178431356" TEXT="类替换简单字段，Reference和value object"/></node><node CREATED="1572178431356" ID="12vukfmp4f6tunjasc5a4lpajt" MODIFIED="1572178431356" TEXT="消除类型差异导致的if-else"/></node><node CREATED="1572178431356" ID="3bud61dr08dqobgkc0bq9eo8r0" MODIFIED="1572178431356" POSITION="left" TEXT="在对象之间搬移特性"><node CREATED="1572178431356" ID="5khs8o26h1m6hf1hbf2056ft0f" MODIFIED="1572178431356" TEXT="Move Method 搬移函数"><node CREATED="1572178431356" ID="6sdi1ttvj4a9nd71bhvvdf3fld" MODIFIED="1572178431356" TEXT="一个类有太多的方法，或者一个类与其他的类高度耦合"/><node CREATED="1572178431356" ID="2fq9d7s5khr7mlqt6ep5a2skad" MODIFIED="1572178431356" TEXT="有时并不能容易决定当前函数是否可以移动，需要考虑一次性移动多个函数"/></node><node CREATED="1572178431356" ID="695caebrj92cvl289omvg6c7r1" MODIFIED="1572178431356" TEXT="Move Field 搬移字段"><node CREATED="1572178431356" ID="68nf5ief2bpcasqfnghgasjt6n" MODIFIED="1572178431356" TEXT="一个字段被其他的类多次使用(通过设值取值调用)"/><node CREATED="1572178431356" ID="02tprtnbq6900jsm22p5gkanju" MODIFIED="1572178431356" TEXT="字段的自我封装 self encapsulate"/></node><node CREATED="1572178431356" ID="4jhfe5p50j96obsvv0of57nv7t" MODIFIED="1572178431356" TEXT="Extract Class 提炼类"><node CREATED="1572178431356" ID="56nmkjtk2adpj9km5uchtk8cla" MODIFIED="1572178431356" TEXT="某些类的职责太多，需要分离一些职责出去"/><node CREATED="1572178431356" ID="6etp78vhg7sbs3kahsgb955s1v" MODIFIED="1572178431356" TEXT="就是综合应用 move field 和 move method"/></node><node CREATED="1572178431356" ID="67ndntco124h6bvuipc9mdshcv" MODIFIED="1572178431356" TEXT="Inline Class 内联类"><node CREATED="1572178431356" ID="4bhb6jqlfsl62rfiq5t4oqrnhu" MODIFIED="1572178431356" TEXT="Extract Class 的反向操作，为了减少职责不够清晰，没有单独存在理由的类"/></node><node CREATED="1572178431356" FOLDER="true" ID="0f945o05smn2vnjko00bu9fue9" MODIFIED="1572178431356" TEXT="Hide Delegate 隐藏委托关系"><node CREATED="1572178431356" ID="6g7598p761qmg652q5phtpf6no" MODIFIED="1572178431356" TEXT="封装掉委托关系，直接在当前类提供操作委托对象的函数，而不需要通过获取委托对象来完成对应的操作"/></node><node CREATED="1572178431356" FOLDER="true" ID="6sf1bcdfh3aeh0tvrhp527aju8" MODIFIED="1572178431356" TEXT="Remove Middle Man 移除中间人"><node CREATED="1572178431356" ID="226737n41m93q06ephui1sj7p7" MODIFIED="1572178431356" TEXT="Hide Delegate 的反向操作，避免在当前函数增加过多的操作委托对象的函数"/></node><node CREATED="1572178431356" FOLDER="true" ID="0uhcv1llomu8gvruv6slbmkrt1" MODIFIED="1572178431356" TEXT="Introduce Foreign Method 引入外加函数"><node CREATED="1572178431356" ID="4gt39lhvh9afts2jt7vl4ma8d0" MODIFIED="1572178431356" TEXT="在客户类中建立一个函数，并以第一参数形式传入服务类实例"/><node CREATED="1572178431356" ID="4j9ugf7hfahg9hv1hrl1vot8vi" MODIFIED="1572178431356" TEXT="如果客户类中的外加函数太多，或者太多客户类使用同一个外加函数，此时需要 Introduce Local Extension"/></node><node CREATED="1572178431356" FOLDER="true" ID="3lss0f6os7rp3ggmslfbupk6si" MODIFIED="1572178431356" TEXT="Introduce Local Extension 引入本地扩展"><node CREATED="1572178431356" ID="3gu3opsc8563gvh3falvah1vc7" MODIFIED="1572178431356" TEXT="子类化"><node CREATED="1572178431356" ID="6m13t9ap06928dni4su9ld2avh" MODIFIED="1572178431356" TEXT="工作量少"/><node CREATED="1572178431356" ID="60umhnuri9jrgk6l7qj0oena5q" MODIFIED="1572178431356" TEXT="需要接管实例化过程，不然无法替换成子类"/></node><node CREATED="1572178431356" ID="6ui6fj0vhfoce5pfvff265afml" MODIFIED="1572178431356" TEXT="包装类"><node CREATED="1572178431356" ID="69fn4771025m0lpm3ubnua58fn" MODIFIED="1572178431356" TEXT="工作量大，需要覆写原来的方法"/></node></node><node CREATED="1572178431356" ID="1ssv92m3niqeeknk182ug0be7c" MODIFIED="1572178431356" TEXT="类重构总结"><node CREATED="1572178431356" ID="37vgpjk05v7r06ggg0thrrcarh" MODIFIED="1572178431356" TEXT="主要是前三个方法，后面都是补充，不一定用的上"/></node><node CREATED="1572178431356" ID="7anq6qlh2bpiqtoj4jl97gvs2i" MODIFIED="1572178431356" TEXT="都要考虑迁移对原来的影响，是保留还是直接替换"/><node CREATED="1572178431356" ID="7tkoeu7ctg6cofbmljmsnohrri" MODIFIED="1572178431356" TEXT="需要两者之间进行博弈"/><node CREATED="1572178431356" ID="5jna5bhbrvfqoa4b7epab72g4k" MODIFIED="1572178431356" TEXT="无法修改源代码时使用，一般情况用不上"/></node><node CREATED="1572178431356" FOLDER="true" ID="0r90e1fbt8emvlor8ki5r8c92j" MODIFIED="1572178431356" POSITION="left" TEXT="重新组织函数"><node CREATED="1572178431356" ID="53ckucpjq5k45fhadp6kohqhph" MODIFIED="1572178431356" TEXT="Extract Method 提炼函数"><node CREATED="1572178431356" ID="5p2tdioo18l1rq6sddgm25c8f4" MODIFIED="1572178431356" TEXT="函数颗粒度小: 复用的机会提高； 函数名就是注释； 函数易于复写"/><node CREATED="1572178431356" ID="6uvevveufuva6dibief636o1pl" MODIFIED="1572178431356" TEXT="函数的长度: 长度不是问题，函数名称和函数本体的语义距离"/><node CREATED="1572178431356" ID="2labv920hf0ag40vlasu7psn7r" MODIFIED="1572178431356" TEXT="注意是否引用或者修改了源函数的参数和局部变量"/></node><node CREATED="1572178431356" ID="1urvc726dj5stbp9cq6u044bip" MODIFIED="1572178431356" TEXT="inline Method(提炼方法的逆向操作)/inline Temp(消除临时变量)"/><node CREATED="1572178431356" ID="1ed3t6qe41g6392lfv4f2p5m3n" MODIFIED="1572178431356" TEXT="Replace Temp with Query(以函数调用替换临时变量)"><node CREATED="1572178431356" ID="1ohm90capavffu7a8nhus6iguk" MODIFIED="1572178431356" TEXT="可能带来性能问题，要反复调用相同的函数"/><node CREATED="1572178431356" ID="7p1ko26p24bt6rtv2j2k02v5p8" MODIFIED="1572178431356" TEXT="局部变量会使得方法难以提炼"/><node CREATED="1572178431356" ID="1p6a9l5c7b8e27o0bnocsa829m" MODIFIED="1572178431356" TEXT="局部变量使得函数变长"/></node><node CREATED="1572178431356" ID="37uaol0dfpfdlafbkbouvrb322" MODIFIED="1572178431356" TEXT="Split Temp Variable 分解临时变量"><node CREATED="1572178431356" ID="57mrlro1mo00t6ha2dtd1kdocb" MODIFIED="1572178431356" TEXT="临时变量只能被赋值一次，不能承担一个以上责任，分解成两个临时变量"/></node><node CREATED="1572178431356" ID="44ci5shpguiaq43mear71mietv" MODIFIED="1572178431356" TEXT="introduce explaining variable 引入解释性变量"><node CREATED="1572178431356" ID="25jrin6ijnpmm91uihsnnjbo1o" MODIFIED="1572178431356" TEXT="使用临时变量来代替复杂的表达式"/></node><node CREATED="1572178431356" ID="3naf00p7ehb6gp8fl5r9im79um" MODIFIED="1572178431356" TEXT="Remove Assignments to Parameters 不要对参数赋值"><node CREATED="1572178431356" ID="549f47kmv0h5vh6b84lms0t5df" MODIFIED="1572178431356" TEXT="保证参数在方法体内的语义不变"/></node><node CREATED="1572178431356" ID="1mmn8p1rha7g2g8qsp7g19k7to" MODIFIED="1572178431356" TEXT="Replace Method with Method Object 以函数对象代替函数"><node CREATED="1572178431356" ID="3mjnv23kfv8oq3112avh7rft7q" MODIFIED="1572178431356" TEXT="把临时变量和方法参数传递到一个方法对象里面，对方法对象的方法进行重构"/></node><node CREATED="1572178431356" ID="6os5422a3r5tdlrocspgg251s0" MODIFIED="1572178431356" TEXT="函数重构总结"><node CREATED="1572178431356" ID="30o0mrdmokeemcn7nh5va2atro" MODIFIED="1572178431356" TEXT="Extract Method (其他的方法都是处理临时变量，为了提炼函数)"/><node CREATED="1572178431356" ID="1s4p02rfh4dp6cnv38suqnqr49" MODIFIED="1572178431356" TEXT="introduce explaining variable(提炼方法不易时使用)"><node CREATED="1572178431356" ID="4olug5n49ruuonkhbitarnj5sq" MODIFIED="1572178431356" TEXT="Replace Method with Method Object"/></node></node><node CREATED="1572178431356" ID="2mpnr94g6nm7hv93hkpn9rni34" MODIFIED="1572178431371" TEXT="局部变量是敌人"/></node><node CREATED="1572178431371" ID="698vd1iigcqp3ritvklla8bbml" MODIFIED="1572178431371" POSITION="left" TEXT="大型重构"><node CREATED="1572178431371" ID="6mkq127g6q2ruhdn7dtpcqvk0e" MODIFIED="1572178431371" TEXT="Tease Apart Inheritance 梳理分解继承体系"><node CREATED="1572178431371" ID="2ue6n4vqvidpk0pgaq7qnurcpn" MODIFIED="1572178431371" TEXT="某个继承体系承担两个责任"/><node CREATED="1572178431371" ID="4ab31qfpocrocmb8u2p9rd31sb" MODIFIED="1572178431371" TEXT="建立两个继承体系，通过委托让一个可以调用另一个"/></node><node CREATED="1572178431371" ID="5snnhg48p0jhj5vn43bqcjdbb4" MODIFIED="1572178431371" TEXT="Convert Procedural Design to Objects 将过程化转化为对象"><node CREATED="1572178431371" ID="42l2b2cf5mce7nnjft2isd0m1q" MODIFIED="1572178431371" TEXT="将大块的行为拆分成小块，并移入相关的对象中"/></node><node CREATED="1572178431371" ID="4og76grtgcp9hvl5c86dtqbrl1" MODIFIED="1572178431371" TEXT="Separate Domain from Presentation 将领域和表述分离"/><node CREATED="1572178431371" ID="0be5pm1kv34g64bltoi82t0sq4" MODIFIED="1572178431371" TEXT="Extract Hierarchy 提炼继承体系"><node CREATED="1572178431371" ID="6185uv2bc60r322vakf9el8idp" MODIFIED="1572178431371" TEXT="某个类承担太多职责，其中一部分功能涉及条件表达式"/></node></node></node></map>