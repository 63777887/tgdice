package com.jwk.tgdice.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GeneratorUtil {

  public GeneratorUtil() {
  }

  public static void main(String[] args) {
    AutoGenerator mpg = new AutoGenerator();
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    GlobalConfig gc = new GlobalConfig();
    gc.setAuthor("jiwk");
    gc.setOutputDir("E:\\learnProject\\tgdice\\src\\main\\java");
    gc.setFileOverride(true);
    gc.setActiveRecord(true);
    gc.setEnableCache(false);
    gc.setBaseResultMap(true);
    gc.setBaseColumnList(false);
    gc.setServiceName("%sService");
    gc.setDateType(DateType.ONLY_DATE);
    mpg.setGlobalConfig(gc);
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(DbType.MYSQL);
    dsc.setTypeConvert(new MySqlTypeConvert() {
      public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        if (fieldType.toLowerCase().contains("tinyint")) {
          System.out.println("转换类型：" + fieldType);
          return DbColumnType.BYTE;
        } else {
          return super.processTypeConvert(config, fieldType);
        }
      }
    });
    dsc.setDriverName("com.mysql.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("Awert159");
    dsc.setUrl(
        "jdbc:mysql://1.13.159.90:3306/dice?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&rewriteBatchedStatements=true&useSSL=false&allowPublicKeyRetrieval=true");
    mpg.setDataSource(dsc);
    PackageConfig pc = new PackageConfig();
    pc.setParent("com.jwk.tgdice.biz");
    pc.setController("controller");
    pc.setEntity("entity");
    pc.setService("service");
    pc.setMapper("mapper");
    mpg.setPackageInfo(pc);
    StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude(new String[]{"dice","dice_amount_type","dice_bet_info","dice_result","dice_play_type","dice_prize","dice_prize_result"});
    strategy.setInclude("dice_account");
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setChainModel(true);
    strategy.setRestControllerStyle(true);
    mpg.setStrategy(strategy);
    mpg.execute();
  }
}
