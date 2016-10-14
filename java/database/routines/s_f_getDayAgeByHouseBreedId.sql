DROP FUNCTION IF EXISTS sparrow_lc.s_f_getDayAgeByHouseBreedId;
CREATE DEFINER=`phoelc`@`%` FUNCTION `s_f_getDayAgeByHouseBreedId`(in_house_breed_id int,in_date DATETIME) RETURNS int(11)
BEGIN
  DECLARE cur_day_age int;
  SELECT day_age INTO cur_day_age from s_b_layer_breed_detail 
  where house_breed_id = in_house_breed_id 
  and date_format(growth_date,'%Y-%m-%d')  = date_format(in_date,'%Y-%m-%d');
  RETURN(cur_day_age);
END;