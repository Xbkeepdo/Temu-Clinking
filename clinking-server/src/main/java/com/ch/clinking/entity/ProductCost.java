package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Arrays;

@ToString
@TableName("productCost")
@Data
public class ProductCost {


    @TableId
    private String skcId;

    private String spuId;

    private String shopId;

    private double cloth = 0;

    private double cutInto = 0;

    private double tailor = 0;

    private double printing = 0;

    private double pack = 0;

    private double total = 0; //总成本

    private double qualityRefundRate = 0;

    private double sizeRefundRate = 0;

    private double more = 0;

    private double qualityScore = 0;

    private double freight = 0; // 袋子运费

    private double button = 0;

    private double ribbing = 0; // 罗纹

    private double rubber = 0; // 橡筋

    private double string = 0; // 绳子

    private double sack = 0; // 袋子



    private double airHole = 0; // 气眼 1
    private double zipper = 0; // 拉链 1
    private double chain = 0; // 链条 1
    private double beltloop = 0; // 耳仔 1
    private double paper = 0; // 衬纸 1
    private double cloth2 = 0; // 其他面料 1
    private double lockHole = 0; // 锁眼 1

    private double otherCraft = 0; // 其他工艺 1
    private double sewButton = 0; // 钉扣子 1

    private String otherFiliaoStr = "";

    private String otherCraftStr = "";




    public double calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        // 累加基本数值字段
        total = total.add(BigDecimal.valueOf(this.cloth));
        total = total.add(BigDecimal.valueOf(this.cutInto));
        total = total.add(BigDecimal.valueOf(this.tailor));
        total = total.add(BigDecimal.valueOf(this.printing));
        total = total.add(BigDecimal.valueOf(this.pack));
        total = total.add(BigDecimal.valueOf(this.qualityRefundRate));
        total = total.add(BigDecimal.valueOf(this.sizeRefundRate));
        total = total.add(BigDecimal.valueOf(this.more));
        total = total.add(BigDecimal.valueOf(this.qualityScore));
        total = total.add(BigDecimal.valueOf(this.freight));
        total = total.add(BigDecimal.valueOf(this.button));
        total = total.add(BigDecimal.valueOf(this.ribbing));
        total = total.add(BigDecimal.valueOf(this.rubber));
        total = total.add(BigDecimal.valueOf(this.string));
        total = total.add(BigDecimal.valueOf(this.sack));
        total = total.add(BigDecimal.valueOf(this.airHole));
        total = total.add(BigDecimal.valueOf(this.zipper));
        total = total.add(BigDecimal.valueOf(this.chain));
        total = total.add(BigDecimal.valueOf(this.beltloop));
        total = total.add(BigDecimal.valueOf(this.paper));
        total = total.add(BigDecimal.valueOf(this.cloth2));
        total = total.add(BigDecimal.valueOf(this.lockHole));
        total = total.add(BigDecimal.valueOf(this.otherCraft));
        total = total.add(BigDecimal.valueOf(this.sewButton));

        // 解析辅料字符串
        total = total.add(parseAdditionalCost(this.otherFiliaoStr));

        // 解析工艺字符串
        total = total.add(parseAdditionalCost(this.otherCraftStr));

        // 四舍五入保留两位小数
        this.total = total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return this.total;
    }

    private BigDecimal parseAdditionalCost(String costStr) {
        if (costStr == null || costStr.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return Arrays.stream(costStr.split("\n"))
                .map(line -> {
                    String[] parts = line.split("\t");
                    if (parts.length >= 2) {
                        try {
                            return new BigDecimal(parts[1].trim());
                        } catch (NumberFormatException e) {
                            return BigDecimal.ZERO;
                        }
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public ProductCost() {
    }

    public ProductCost(String skcId, String spuId, String shopId, double cloth,
                       double cutInto, double tailor, double printing, double pack, double total,
                       double qualityRefundRate, double sizeRefundRate, double more, double qualityScore, double freight, double button,
                       double ribbing, double rubber, double string, double sack,
                       double airHole, double zipper, double chain, double beltloop, double paper,
                       double cloth2, double lockHole, double otherCraft, double sewButton,
                       String otherFiliaoStr,String otherCraftStr) {
        this.skcId = skcId;
        this.spuId = spuId;
        this.shopId = shopId;
        this.cloth = cloth;
        this.cutInto = cutInto;
        this.tailor = tailor;
        this.printing = printing;
        this.pack = pack;
        this.total = total;
        this.qualityRefundRate = qualityRefundRate;
        this.sizeRefundRate = sizeRefundRate;
        this.more = more;
        this.qualityScore = qualityScore;
        this.freight = freight;
        this.button = button;
        this.ribbing = ribbing;
        this.rubber = rubber;
        this.string = string;
        this.sack = sack;
        this.airHole = airHole;
        this.zipper = zipper;
        this.chain = chain;
        this.beltloop = beltloop;
        this.paper = paper;
        this.cloth2 = cloth2;
        this.lockHole = lockHole;
        this.otherCraft = otherCraft;
        this.sewButton = sewButton;
        this.otherFiliaoStr = otherFiliaoStr;
        this.otherCraftStr = otherCraftStr;
    }

    public ProductCost(String spuId, String skcId, String shopId) {
        this.spuId = spuId;
        this.skcId = skcId;
        this.shopId = shopId;
    }

    // Getters and Setters
    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Double getCloth() {
        return cloth;
    }

    public void setCloth(Double cloth) {
        this.cloth = cloth;
    }

    public Double getCutInto() {
        return cutInto;
    }

    public void setCutInto(Double cutInto) {
        this.cutInto = cutInto;
    }

    public Double getTailor() {
        return tailor;
    }

    public void setTailor(Double tailor) {
        this.tailor = tailor;
    }

    public Double getPrinting() {
        return printing;
    }

    public void setPrinting(Double printing) {
        this.printing = printing;
    }

    public Double getPack() {
        return pack;
    }

    public void setPack(Double pack) {
        this.pack = pack;
    }

    public Double getMore() {
        return more;
    }

    public void setMore(Double more) {
        this.more = more;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getQualityRefundRate() {
        return qualityRefundRate;
    }

    public void setQualityRefundRate(Double qualityRefundRate) {
        this.qualityRefundRate = qualityRefundRate;
    }

    public Double getSizeRefundRate() {
        return sizeRefundRate;
    }

    public void setSizeRefundRate(Double sizeRefundRate) {
        this.sizeRefundRate = sizeRefundRate;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getButton() {
        return button;
    }

    public void setButton(Double button) {
        this.button = button;
    }

    public Double getRibbing() {
        return ribbing;
    }

    public void setRibbing(Double ribbing) {
        this.ribbing = ribbing;
    }

    public Double getRubber() {
        return rubber;
    }

    public void setRubber(Double rubber) {
        this.rubber = rubber;
    }

    public Double getString() {
        return string;
    }

    public void setString(Double string) {
        this.string = string;
    }

    public Double getSack() {
        return sack;
    }

    public void setSack(Double sack) {
        this.sack = sack;
    }

    public Double getAirHole() {
        return airHole;
    }

    public void setAirHole(Double airHole) {
        this.airHole = airHole;
    }

    public Double getZipper() {
        return zipper;
    }

    public void setZipper(Double zipper) {
        this.zipper = zipper;
    }

    public Double getChain() {
        return chain;
    }

    public void setChain(Double chain) {
        this.chain = chain;
    }

    public Double getBeltloop() {
        return beltloop;
    }

    public void setBeltloop(Double beltloop) {
        this.beltloop = beltloop;
    }

    public Double getPaper() {
        return paper;
    }

    public void setPaper(Double paper) {
        this.paper = paper;
    }

    public Double getCloth2() {
        return cloth2;
    }

    public String getOtherCraftStr() {
        return otherCraftStr;
    }

    public void setOtherCraftStr(String otherCraftStr) {
        this.otherCraftStr = otherCraftStr;
    }

    @Override
    public String toString() {
        return "ProductCost{" +
                "skcId='" + skcId + '\'' +
                ", spuId='" + spuId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", cloth=" + cloth +
                ", cutInto=" + cutInto +
                ", tailor=" + tailor +
                ", printing=" + printing +
                ", pack=" + pack +
                ", more=" + more +
                ", total=" + total +
                ", qualityRefundRate=" + qualityRefundRate +
                ", sizeRefundRate=" + sizeRefundRate +
                ", qualityScore=" + qualityScore +
                ", freight=" + freight +
                ", button=" + button +
                ", ribbing=" + ribbing +
                ", rubber=" + rubber +
                ", string=" + string +
                ", sack=" + sack +
                ", airHole=" + airHole +
                ", zipper=" + zipper +
                ", chain=" + chain +
                ", beltloop=" + beltloop +
                ", paper=" + paper +
                ", cloth2=" + cloth2 +
                ", lockHole=" + lockHole +
                ", otherCraft=" + otherCraft +
                ", sewButton=" + sewButton +
                ", otherFiliaoStr='" + otherFiliaoStr + '\'' +
                '}';
    }
}