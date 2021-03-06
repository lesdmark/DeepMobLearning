package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemLivingMatter;
import xt9.deepmoblearning.common.items.ItemPristineMatter;
import xt9.deepmoblearning.common.util.MathHelper;

import java.util.List;

/**
 * Created by xt9 on 2017-06-09.
 */
public abstract class MobMetaData {
    protected String name;
    private String pluralName;
    protected String key;
    protected int numberOfHearts;
    protected int interfaceScale;
    protected int interfaceOffsetX;
    protected int interfaceOffsetY;
    protected ItemLivingMatter livingMatter;
    protected ItemPristineMatter pristineMatter;
    protected String[] mobTrivia;

    private String[] mobRegistryNames;

    public MobMetaData(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter, String[] mobTrivia) {
        this.key = key;
        this.name = name;
        this.pluralName = pluralName;
        this.numberOfHearts = numberOfHearts;
        this.interfaceScale = interfaceScale;
        this.interfaceOffsetX = interfaceOffsetX;
        this.interfaceOffsetY = interfaceOffsetY;
        this.livingMatter = (ItemLivingMatter) livingMatter;
        this.pristineMatter = (ItemPristineMatter) pristineMatter;
        this.mobTrivia = mobTrivia;
    }

    public int getSimulationTickCost() {
        int cost = Config.dataModelRFCost.get(getKey());
        cost = MathHelper.ensureRange(cost, 1, DeepConstants.MAX_DATA_MODEL_COST);
        return cost;
    }
    public ItemStack getLivingMatterStack(int amount) {
        return new ItemStack(livingMatter, amount);
    }

    public ItemStack getPristineMatterStack(int amount) {
        return new ItemStack(pristineMatter, amount);
    }

    public String getName() {
        return name;
    }

    public String getPluralName() {
        return pluralName;
    }

    public String getKey() {
        return key;
    }

    public int getNumberOfHearts() {
        return numberOfHearts;
    }

    public int getInterfaceScale() {
        return interfaceScale;
    }

    public int getInterfaceOffsetX() {
        return interfaceOffsetX;
    }

    public int getInterfaceOffsetY() {
        return interfaceOffsetY;
    }

    public String getMatterTypeName() {
        return livingMatter.getMatterTypeName();
    }

    public ItemLivingMatter getLivingMatter() {
        return livingMatter;
    }

    public ItemPristineMatter getPristineMatter() {
        return pristineMatter;
    }

    public String[] getMobTrivia() {
        return mobTrivia;
    }

    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        ResourceLocation registryName = entityLiving.getType().getRegistryName();
        if (registryName != null) {
            String name = registryName.toString();

            for (String mobRegname : Config.dataModelMobs.get(getKey())) {
                if (mobRegname.equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Have to implement, different for every Meta
    @OnlyIn(Dist.CLIENT)
    public abstract Entity getEntity(World world);

    // Optional fields
    @OnlyIn(Dist.CLIENT)
    public Entity getExtraEntity(World world) {
        return null;
    }

    public int getExtraInterfaceOffsetX() {
        return 0;
    }

    public int getExtraInterfaceOffsetY() {
        return 0;
    }

    public String getExtraTooltip() {
        return null;
    }
}
