package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.Booking;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.BookingDto;
import com.vehiclerentalservices.app.repositary.BookingRepositary;
import com.vehiclerentalservices.app.repositary.UserRepositary;
import com.vehiclerentalservices.app.repositary.VehicleRepositary;
import com.vehiclerentalservices.app.services.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private BookingRepositary bookingRepo;
	
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	private VehicleRepositary vehicleRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<BookingDto> getAllBooking() {
		List<Booking> book = this.bookingRepo.findAll();
		List<BookingDto> books = book.stream()
				.map(booklist-> this.bookingToDto(booklist)).collect(Collectors.toList());
		return books;
	}

	@Override
	public BookingDto getBookingById(Integer id) {
		Booking book = this.bookingRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Booking", "id", id));
		return this.bookingToDto(book);
	}

	@Override
	public BookingDto createBooking(BookingDto bookDto, Integer uId, Integer vId) {
		User user = this.userRepo.findById(uId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", uId));
		Vehicle vehicle = this.vehicleRepo.findById(vId)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", vId));
		Booking book = this.dtoToBooking(bookDto);
		book.setUser(user);
		book.setVehicle(vehicle);
		Booking books = this.bookingRepo.save(book);
		return this.bookingToDto(books);
	}

	@Override
	public BookingDto updateBooking(BookingDto bookDto, Integer id) {
		Booking book = this.bookingRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Booking", "id", id));
		
		book.setFlag(bookDto.getFlag());
		
		Booking books = this.bookingRepo.save(book);
		BookingDto updateBook = this.bookingToDto(books);
		return updateBook;
	}

	@Override
	public void deleteBooking(Integer id) {
		Booking book = this.bookingRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Booking", "id", id));
		this.bookingRepo.delete(book);

	}

	@Override
	public List<BookingDto> getAllByUser(Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		List<Booking> book = this.bookingRepo.findAllByUser(user);
		
		List<BookingDto> list = book.stream()
				.map(books-> this.bookingToDto(books)).collect(Collectors.toList());
		return list;
	}

	@Override
	public List<BookingDto> getAllByVehicle(Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		List<Booking> book = this.bookingRepo.findAllByVehicle(vehicle);
		
		List<BookingDto> list =book.stream()
				.map(books-> this.bookingToDto(books)).collect(Collectors.toList());
		return list;
	}
	
	private Booking dtoToBooking(BookingDto bookDto) {
		
		Booking book = this.mapper.map(bookDto, Booking.class);
		return book;
		
	}
	
	
	public BookingDto bookingToDto(Booking book) {
		
		BookingDto bookdto = this.mapper.map(book, BookingDto.class);
		return bookdto;
		
	}

	@Override
	public List<BookingDto> getByFlag(Integer flag, Integer uid) {
		List<Booking> booking = this.bookingRepo.findByFlag(flag); 
		List<BookingDto> book = null;
		for(Booking bookings : booking) {
			Vehicle vehicle = this.vehicleRepo.findById(bookings.getVehicle().getId())
					.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", 0));
			System.out.print("id are : "+vehicle.getUser().getId());
			if(vehicle != null && vehicle.getUser().getId() == uid) {
				book = booking.stream().map(books -> this.bookingToDto(books)).collect(Collectors.toList()); 
			}
		}
		return book;
	}

	@Override
	public List<BookingDto> getByFlagUser(Integer flag) {
		List<Booking> booking = this.bookingRepo.findByFlag(flag);
		List<BookingDto> book = booking.stream().map(books -> this.bookingToDto(books)).collect(Collectors.toList());
		return book;
	}
}
