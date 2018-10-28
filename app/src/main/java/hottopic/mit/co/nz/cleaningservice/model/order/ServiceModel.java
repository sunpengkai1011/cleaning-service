package hottopic.mit.co.nz.cleaningservice.model.order;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning.TimerCleaningType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ironing.ClothesType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning.AreaCleaningType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.cleaning.SubOption;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ServiceType;
import hottopic.mit.co.nz.cleaningservice.entities.orders.ironing.IroningType;

public class ServiceModel implements IService {
    private Context context;

    public ServiceModel(Context context) {
        this.context = context;
    }

    @Override
    public List<ServiceType> getCleaningTypes() {
        return createCleaningTypes();
    }

    @Override
    public List<ServiceType> getIroningTypes() {
        return createIroningTypes();
    }

    private List<ServiceType> createCleaningTypes(){
        SubOption gc_1bedroom = new SubOption(Constants.SUB_OPTION_1BEDROOM, "1 Bedroom", 30);
        SubOption gc_2bedrooms = new SubOption(Constants.SUB_OPTION_2BEDROOMS, "2 Bedrooms", 55);
        SubOption gc_3bedrooms = new SubOption(Constants.SUB_OPTION_3BEDROOMS, "3 Bedrooms", 80);
        SubOption gc_4bedrooms = new SubOption(Constants.SUB_OPTION_4BEDROOMS, "4 Bedrooms", 105);
        List<SubOption> gc_options = new ArrayList<>();
        gc_options.add(gc_1bedroom);
        gc_options.add(gc_2bedrooms);
        gc_options.add(gc_3bedrooms);
        gc_options.add(gc_4bedrooms);
        TimerCleaningType gc_cleaning = new TimerCleaningType(Constants.ID_SERVICE_G_CLEANING, "General Cleaning",  R.drawable.icon_g_cleaning, gc_options);

        SubOption dc_1bedroom = new SubOption(Constants.SUB_OPTION_4BEDROOMS, "1 Bedroom", 50);
        SubOption dc_2bedrooms = new SubOption(Constants.SUB_OPTION_4BEDROOMS, "2 Bedrooms", 95);
        SubOption dc_3bedrooms = new SubOption(Constants.SUB_OPTION_4BEDROOMS, "3 Bedrooms", 140);
        SubOption dc_4bedrooms = new SubOption(Constants.SUB_OPTION_4BEDROOMS, "4 Bedrooms", 185);
        List<SubOption> dc_options = new ArrayList<>();
        dc_options.add(dc_1bedroom);
        dc_options.add(dc_2bedrooms);
        dc_options.add(dc_3bedrooms);
        dc_options.add(dc_4bedrooms);
        TimerCleaningType dc_cleaning = new TimerCleaningType(Constants.ID_SERVICE_D_CLEANING, "Deep Cleaning", R.drawable.icon_d_cleaning, dc_options);
        List<ServiceType> cleaningTypes = new ArrayList<>();
        cleaningTypes.add(gc_cleaning);
        cleaningTypes.add(dc_cleaning);

        AreaCleaningType buffering = new AreaCleaningType(Constants.ID_SERVICE_BUFFERING, "Buffering", R.drawable.icon_buffering, 8);
        AreaCleaningType waterBlasting = new AreaCleaningType(Constants.ID_SERVICE_WATERBLASTING, "Water Blasting", R.drawable.icon_water_blast, 6);
        AreaCleaningType carpetWash = new AreaCleaningType(Constants.ID_SERVICE_CARPETWASH, "Carpet Washing",  R.drawable.icon_carpet_wash, 5);
        cleaningTypes.add(buffering);
        cleaningTypes.add(waterBlasting);
        cleaningTypes.add(carpetWash);
        return cleaningTypes;
    }

    private List<ServiceType> createIroningTypes(){
        List<ServiceType> ironingTypes= new ArrayList<>();
        List<ClothesType> g_cleaningTypes = new ArrayList<>();
        List<ClothesType> s_cleaningTypes = new ArrayList<>();
        ClothesType g_shirt = new ClothesType(Constants.ID_CLOTHES_SHIRT, R.drawable.icon_shirt, "Shirt", 3);
        ClothesType g_jacket = new ClothesType(Constants.ID_CLOTHES_JACKET, R.drawable.icon_jacket, "Jacket", 4);
        ClothesType g_long_dress = new ClothesType(Constants.ID_CLOTHES_LONG_DRESS, R.drawable.icon_long_dress, "Long Dress", 4);
        ClothesType g_school_uniform = new ClothesType(Constants.ID_CLOTHES_SCHOOL_UNIFORM, R.drawable.icon_uniform, "School Uniform", 6);
        g_cleaningTypes.add(g_shirt);
        g_cleaningTypes.add(g_jacket);
        g_cleaningTypes.add(g_long_dress);
        g_cleaningTypes.add(g_school_uniform);
        ClothesType s_shirt = new ClothesType(Constants.ID_CLOTHES_SHIRT, R.drawable.icon_shirt, "Shirt", 4);
        ClothesType s_jacket = new ClothesType(Constants.ID_CLOTHES_JACKET, R.drawable.icon_jacket, "Jacket", 5);
        ClothesType s_long_dress = new ClothesType(Constants.ID_CLOTHES_LONG_DRESS, R.drawable.icon_long_dress, "Long Dress", 5);
        ClothesType s_school_uniform = new ClothesType(Constants.ID_CLOTHES_SCHOOL_UNIFORM, R.drawable.icon_uniform, "School Uniform", 7);
        s_cleaningTypes.add(s_shirt);
        s_cleaningTypes.add(s_jacket);
        s_cleaningTypes.add(s_long_dress);
        s_cleaningTypes.add(s_school_uniform);
        IroningType general_ironing = new IroningType(Constants.ID_SERVICE_G_INRONING, "General Ironing", R.drawable.icon_iron,g_cleaningTypes, 0.9f);
        IroningType steam_ironing = new IroningType(Constants.ID_SERVICE_S_INRONING, "Steam Ironing", R.drawable.icon_steam, s_cleaningTypes, 0.9f);
        ironingTypes.add(general_ironing);
        ironingTypes.add(steam_ironing);
        return ironingTypes;
    }
}
