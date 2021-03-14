package com.traders.application.items.service;

import com.traders.application.items.entity.Items;
import com.traders.application.items.entity.Users;
import com.traders.application.items.exception.ItemCreationFailedException;
import com.traders.application.items.exception.ItemNotFoundException;
import com.traders.application.items.exception.UserNotFoundException;
import com.traders.application.items.repository.ItemRepository;
import com.traders.application.items.repository.UserRepository;
import com.traders.application.items.util.ConstantsFile;
import com.traders.application.items.util.CurrencyExchangeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CurrencyExchangeUtility currencyExchangeUtility;

    public String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Logged In UserName is: " + auth.getName());
        return auth.getName();
    }

    public List<Items> getAllItemsForTrader() {

        String loggedInUser = getLoggedInUserName();
        Optional<Users> user = userRepository.findByUsername(loggedInUser);

        if (user.isPresent()) {

            if (!user.get().getItemsList().isEmpty()) {
                return currencyExchangeUtility.ConvertCurrency(user.get().getItemsList());
            } else {
                logger.debug(ConstantsFile.NO_ITEM_EXISTS + loggedInUser);
                throw new ItemNotFoundException(ConstantsFile.NO_ITEM_EXISTS + loggedInUser);
            }
        } else {
            logger.debug(loggedInUser + ConstantsFile.USER_NOT_EXISTS);
            throw new UserNotFoundException(loggedInUser + ConstantsFile.USER_NOT_EXISTS);
        }
    }

    public Items createItem(Items item) {

        String loggedInUser = getLoggedInUserName();
        Optional<Users> user = userRepository.findByUsername(loggedInUser);

        if (user.isPresent()) {

            Items savedItem;
            try {
                item.setUserId(user.get().getId());
                savedItem = itemRepository.save(item);
            } catch (Exception e) {
                logger.debug("Item cannot be created!", e);
                throw new ItemCreationFailedException("Item cannot be created!");
            }
            return savedItem;

        } else {
            logger.debug(loggedInUser + ConstantsFile.USER_NOT_EXISTS);
            throw new UserNotFoundException(loggedInUser + ConstantsFile.USER_NOT_EXISTS);
        }
    }

    public Items updateItem(Items item) {

        String loggedInuser = getLoggedInUserName();

        Optional<Users> user = userRepository.findByUsername(loggedInuser);

        if (user.isPresent()) {

            Optional<Items> itemById = itemRepository.findById(item.getId());

            itemById.orElseThrow(() -> new ItemNotFoundException("Item does not exists"));

            Items updatedItem;

            try {
                itemById.get().setItemName(item.getItemName());
                itemById.get().setDescription(item.getDescription());
                itemById.get().setPrice(item.getPrice());
                itemById.get().setCurrency(item.getCurrency());
                updatedItem = itemRepository.save(itemById.get());

            } catch (Exception e) {
                throw new ItemCreationFailedException("Item cannot be updated");
            }

            return updatedItem;


        } else {
            throw new UserNotFoundException(loggedInuser + ConstantsFile.USER_NOT_EXISTS);
        }

    }

    public Items getItemForTrader(Integer itemId) {

        String loggedInUser = getLoggedInUserName();
        Optional<Users> user = userRepository.findByUsername(loggedInUser);

        if (user.isPresent()) {

            if (!user.get().getItemsList().isEmpty()) {
                Optional<Items> itemById = user.get().getItemsList()
                        .stream()
                        .filter(item -> item.getId() == itemId)
                        .findFirst();

                itemById.orElseThrow(() -> new ItemNotFoundException("Item Id Does Not Exists!!!"));

                List<Items> itemsList = currencyExchangeUtility.ConvertCurrency(Arrays.asList(itemById.get()));

                return itemsList.get(0);
            } else {
                throw new ItemNotFoundException(" No Item Exists for Trader " + loggedInUser);
            }
        } else {
            throw new UserNotFoundException(loggedInUser + " User Does Not Exists!!!");
        }
    }

    public String deleteItem(Integer itemId) {

        String loggedInUser = getLoggedInUserName();
        Optional<Users> user = userRepository.findByUsername(loggedInUser);

        if (user.isPresent()) {

            try {

                Optional<Items> itemById = itemRepository.findById(itemId);
                itemById.orElseThrow(() -> new ItemNotFoundException("Cannot find item to delete by Id " + itemId));
                itemRepository.delete(itemById.get());
                return "Item Deleted Successfully!!!";

            } catch (Exception e) {
                logger.debug("Item cannot be Deleted!", e);
                throw new ItemCreationFailedException("Item cannot be Deleted!");
            }

        } else {
            logger.debug(loggedInUser + " User Does Not Exists!!!");
            throw new UserNotFoundException("User Does Not Exists!!!");
        }
    }
}
