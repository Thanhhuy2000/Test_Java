public class VehicleFactory {
    
    public static Vehicle createVehicle(String type, String id, String brand, int year, Object... additionalParams) throws VehicleException {
        switch (type.toLowerCase()) {
            case "motorbike":
            case "xe máy":
                if (additionalParams.length < 1) {
                    throw new VehicleException("Xe máy cần thông tin công suất");
                }
                int power = (Integer) additionalParams[0];
                return new Motorbike(id, brand, year, power);
                
            case "car":
            case "ô tô":
                if (additionalParams.length < 1) {
                    throw new VehicleException("Ô tô cần thông tin số chỗ ngồi");
                }
                int seats = (Integer) additionalParams[0];
                return new Car(id, brand, year, seats);
                
            case "truck":
            case "xe tải":
                if (additionalParams.length < 1) {
                    throw new VehicleException("Xe tải cần thông tin tải trọng");
                }
                double loadCapacity = (Double) additionalParams[0];
                return new Truck(id, brand, year, loadCapacity);
                
            default:
                throw new VehicleException("Loại phương tiện không hợp lệ: " + type);
        }
    }
    
    public static Vehicle createVehicleFromString(String vehicleString) throws VehicleException {
        String[] parts = vehicleString.split(",");
        if (parts.length < 4) {
            throw new VehicleException("Dữ liệu không đủ thông tin");
        }
        
        String type = parts[0].trim();
        String id = parts[1].trim();
        String brand = parts[2].trim();
        int year = Integer.parseInt(parts[3].trim());
        
        switch (type.toLowerCase()) {
            case "motorbike":
            case "xe máy":
                if (parts.length < 5) {
                    throw new VehicleException("Thiếu thông tin công suất cho xe máy");
                }
                int power = Integer.parseInt(parts[4].trim());
                return new Motorbike(id, brand, year, power);
                
            case "car":
            case "ô tô":
                if (parts.length < 5) {
                    throw new VehicleException("Thiếu thông tin số chỗ cho ô tô");
                }
                int seats = Integer.parseInt(parts[4].trim());
                return new Car(id, brand, year, seats);
                
            case "truck":
            case "xe tải":
                if (parts.length < 5) {
                    throw new VehicleException("Thiếu thông tin tải trọng cho xe tải");
                }
                double loadCapacity = Double.parseDouble(parts[4].trim());
                return new Truck(id, brand, year, loadCapacity);
                
            default:
                throw new VehicleException("Loại phương tiện không hợp lệ: " + type);
        }
    }
} 