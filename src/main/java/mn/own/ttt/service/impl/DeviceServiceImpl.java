package mn.own.ttt.service.impl;

import java.util.Optional;
import mn.own.ttt.domain.Device;
import mn.own.ttt.repository.DeviceRepository;
import mn.own.ttt.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        return deviceRepository.save(device);
    }

    @Override
    public Optional<Device> partialUpdate(Device device) {
        log.debug("Request to partially update Device : {}", device);

        return deviceRepository
            .findById(device.getId())
            .map(existingDevice -> {
                if (device.getDeviceName() != null) {
                    existingDevice.setDeviceName(device.getDeviceName());
                }
                if (device.getDeviceCode() != null) {
                    existingDevice.setDeviceCode(device.getDeviceCode());
                }
                if (device.getDeviceType() != null) {
                    existingDevice.setDeviceType(device.getDeviceType());
                }
                if (device.getStatus() != null) {
                    existingDevice.setStatus(device.getStatus());
                }
                if (device.getCreatedBy() != null) {
                    existingDevice.setCreatedBy(device.getCreatedBy());
                }
                if (device.getCreatedDate() != null) {
                    existingDevice.setCreatedDate(device.getCreatedDate());
                }
                if (device.getLastModifiedBy() != null) {
                    existingDevice.setLastModifiedBy(device.getLastModifiedBy());
                }
                if (device.getLastModifiedDate() != null) {
                    existingDevice.setLastModifiedDate(device.getLastModifiedDate());
                }

                return existingDevice;
            })
            .map(deviceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Device> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }
}
