package com.gl.mdr.mapper;
import com.gl.mdr.dto.StolenLostModelDto;
import com.gl.mdr.model.app.StolenLostModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StolenLostModelMapper {

    // Create the instance of the mapper
    StolenLostModelMapper INSTANCE = Mappers.getMapper(StolenLostModelMapper.class);

    // Map StolenLostModel to StolenLostModelDto
    StolenLostModelDto stolenLostModelToDto(StolenLostModel stolenLostModel);




    public static StolenLostModelDto stolenDto(StolenLostModel stolenLostModel){
        StolenLostModelDto stolenLostModelDto= new StolenLostModelDto();
        stolenLostModelDto.setRequestType(stolenLostModel.getRequestType());
        stolenLostModelDto.setDeviceOwnerProvinceCity(stolenLostModel.getDeviceOwnerProvinceCity());
        stolenLostModelDto.setDeviceOwnerCommune(stolenLostModel.getDeviceOwnerCommune());
        stolenLostModelDto.setDistrict(stolenLostModel.getDistrict());

        stolenLostModelDto.setBrowser(stolenLostModel.getBrowser());
        stolenLostModelDto.setCategory(stolenLostModel.getCategory());
        stolenLostModelDto.setDeviceModel(stolenLostModel.getDeviceModel());

        return stolenLostModelDto;
    }
}
