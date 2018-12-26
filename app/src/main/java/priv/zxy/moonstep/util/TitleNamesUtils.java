package priv.zxy.moonstep.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.title.MoonTitleModel;

public class TitleNamesUtils {

    public static Collection<MoonTitleModel> generateTitleNames(){
        List<MoonTitleModel> titleNames = new ArrayList<>(21);

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月民之微")
                    .withTitleLevel("一阶")
                    .withImageResId(R.drawable.title_0)
                    .withBackgroundColorResId(R.color.Yellow)
                    .withTitleDescription("圆月完成度10%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月史之长")
                    .withTitleLevel("二阶")
                    .withImageResId(R.drawable.title_1)
                    .withBackgroundColorResId(R.color.Red)
                    .withTitleDescription("圆月完成度20%~30%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月玄之气")
                    .withTitleLevel("三阶")
                    .withImageResId(R.drawable.title_2)
                    .withBackgroundColorResId(R.color.RoyalBlue)
                    .withTitleDescription("圆月完成度40%~50%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月候之力")
                    .withTitleLevel("四阶")
                    .withImageResId(R.drawable.title_3)
                    .withBackgroundColorResId(R.color.SlateBlue)
                    .withTitleDescription("圆月完成度60%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月王之盛")
                    .withTitleLevel("五阶")
                    .withImageResId(R.drawable.title_4)
                    .withBackgroundColorResId(R.color.SpringGreen)
                    .withTitleDescription("圆月完成度70%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月皇之尊")
                    .withTitleLevel("六阶")
                    .withImageResId(R.drawable.title_5)
                    .withBackgroundColorResId(R.color.Yellow)
                    .withTitleDescription("圆月完成度80%%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月圣之灵")
                    .withTitleLevel("七阶")
                    .withImageResId(R.drawable.title_6)
                    .withBackgroundColorResId(R.color.Blue)
                    .withTitleDescription("圆月完成度90%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月神之殇")
                    .withTitleLevel("八阶")
                    .withImageResId(R.drawable.title_7)
                    .withBackgroundColorResId(R.color.CadetBlue)
                    .withTitleDescription("圆月完成度100%")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月老的青睐")
                    .withTitleLevel("二阶")
                    .withImageResId(R.drawable.title_8)
                    .withBackgroundColorResId(R.color.Crimson)
                    .withTitleDescription("得到月老的任何一个奖励物品可以获得")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月老的红人")
                    .withTitleLevel("三阶")
                    .withImageResId(R.drawable.title_9)
                    .withBackgroundColorResId(R.color.DarkGreen)
                    .withTitleDescription("得到月老的三件物品可以获得")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月老的代言人")
                    .withTitleLevel("五阶")
                    .withImageResId(R.drawable.title_10)
                    .withBackgroundColorResId(R.color.DarkMagenta)
                    .withTitleDescription("得到月老的所有物品可以获得")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("勘测者")
                    .withTitleLevel("四阶")
                    .withImageResId(R.drawable.title_11)
                    .withBackgroundColorResId(R.color.DarkVoilet)
                    .withTitleDescription("获得过雷达物品")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("小偷的全貌")
                    .withTitleLevel("二阶")
                    .withImageResId(R.drawable.title_12)
                    .withBackgroundColorResId(R.color.DeepPink)
                    .withTitleDescription("得到过小偷的眼球")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月族之友")
                    .withTitleLevel("六阶")
                    .withImageResId(R.drawable.title_13)
                    .withBackgroundColorResId(R.color.DoderBlue)
                    .withTitleDescription("有100个月友的契合度达到100")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("冒险家")
                    .withTitleLevel("四阶")
                    .withImageResId(R.drawable.title_14)
                    .withBackgroundColorResId(R.color.Gray)
                    .withTitleDescription("通过地图达到5个不同的地点获得奖励")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("远方的邦交者")
                    .withTitleLevel("五阶")
                    .withImageResId(R.drawable.title_15)
                    .withBackgroundColorResId(R.color.Indigo)
                    .withTitleDescription("通过地图达到10个不同的地点获得奖励")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("传说中的远行家")
                    .withTitleLevel("七阶")
                    .withImageResId(R.drawable.title_16)
                    .withBackgroundColorResId(R.color.Yellow)
                    .withTitleDescription("通过地图达到100个不同的地点获得奖励")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("学者")
                    .withTitleLevel("二阶")
                    .withImageResId(R.drawable.title_17)
                    .withBackgroundColorResId(R.color.Lime)
                    .withTitleDescription("得到过知识手册")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("带刺的玫瑰")
                    .withTitleLevel("三阶")
                    .withImageResId(R.drawable.title_18)
                    .withBackgroundColorResId(R.color.LightSlateGray)
                    .withTitleDescription("得到过友谊的荆棘")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月魔的证明")
                    .withTitleLevel("七阶")
                    .withImageResId(R.drawable.title_19)
                    .withBackgroundColorResId(R.color.Black)
                    .withTitleDescription("身为月魔族，可以获得此称号，月魔种族失去，称号失去")
                    .build());
        }

        {
            titleNames.add(MoonTitleModel
                    .newBuilder()
                    .withTitleName("月神的光明")
                    .withTitleLevel("八阶")
                    .withImageResId(R.drawable.title_20)
                    .withBackgroundColorResId(R.color.Gold)
                    .withTitleDescription("身为月魔族，可以获得此称号，月魔种族失去，称号失去")
                    .build());
        }
        return titleNames;
    }
}
